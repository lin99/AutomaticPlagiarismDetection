import numpy as np
import sklearn.cluster as sklCluster
import sklearn.manifold as sklManifold
import matplotlib.pyplot as plt
import networkx as nx
import plotly 
import plotly.plotly as py
from plotly.graph_objs import *
from plotly.tools import FigureFactory as FF
from sklearn.cluster import KMeans
from tables import *
from sklearn import metrics


plotly.offline.init_notebook_mode()


def scatter_nodes(pos, clusters=None, labels=None, color=None, size=20, opacity=.7):
    # pos is the dict of node positions
    # labels is a list  of labels of len(pos), to be displayed when hovering the mouse over the nodes
    # color is the color for nodes. When it is set as None the Plotly default color is used
    # size is the size of the dots representing the nodes
    #opacity is a value between [0,1] defining the node color opacity
    L=len(pos)
    trace = Scatter(x=[], y=[],  mode='markers',text = [], marker=Marker(size=[], colorscale='Portland', color=[]))
    for k in pos.keys():
        
        #trace['text'].append(k)
        trace['x'].append(pos[k][0])
        trace['y'].append(pos[k][1])
        trace['marker']['color'].append(clusters[k])
    
       
    attrib=dict(name='', text = labels.values() , hoverinfo = 'text', opacity=opacity) # a dict of Plotly node attributes
    trace=dict(trace, **attrib)# concatenate the dict trace and attrib
    trace['marker']['size'] = size
    return trace

def scatter_edges(G, pos, similitudes, line_color=None, line_width=1):
    traces = []
    for edge in G.edges():
        # similitudes[edge[0]][edge[1]],color='#000'
        trace = Scatter(x=[], y=[], mode='lines', line=Line(width = 1 ,color='#888'))
        trace['x'] += [pos[edge[0]][0],pos[edge[1]][0], None]
        trace['y'] += [pos[edge[0]][1],pos[edge[1]][1], None]  
        #trace['hoverinfo']='none'
        attrib = dict(text = similitudes, hoverinfo = 'text')
        trace['hoverinfo'] = 'none'
        traces.append(trace)
        #traces = dict(trace, **attrib)
    return traces

def make_annotations(pos, text, font_size=14, font_color='rgb(0,0,0)'):
    L=len(pos)
    if len(text)!=L:
        raise ValueError('The lists pos {} and text {} must have the same len'.format(L, len(text)))
    annotations = Annotations()
    
    for k in pos.keys():
        
        annotations.append(
            
            Annotation(
                text = text[k], 
                x = pos[k][0], y = pos[k][1],
        
                xref='x1', yref='y1',
                font=dict(color= font_color, size=font_size),
                showarrow=False)
        )
    
    return annotations

axis=dict(showline=False, # hide axis line, grid, ticklabels and  title
          zeroline=False,
          showgrid=False,
          showticklabels=False,
          title='' 
          )

width=800
height=800
layout=Layout(title= "Similitud entre soluciones",  
    font= Font(size=12),
    showlegend=False,
    autosize=False,
    width=width,
    height=height,
    xaxis=XAxis(axis),
    yaxis=YAxis(axis),          
    margin=Margin(
        l=40,
        r=40,
        b=85,
        t=100,
    ),
    hovermode='closest',           
    )




##########
data = open("users.txt")
lines = data.read().splitlines()
print lines
users = []
for l in lines:
    users.append(l.split())
######
data = open("contest.txt")
line = data.read()
contest = int(line[1:])

######
h5file = open_file("JudgeVeredicts1.h5", mode="r")

print h5file

short = h5file.root.veredicts.information

veredicts_dict = {}
for k in short:
    if(k['cid'] == contest):
        #if(k['submitid'] not in veredicts):
        veredicts_dict[int(k['submitid'])] = k['result']
#####
distances = np.loadtxt("distances.txt")#[:50,:50]
gamma = 0.1

similitudes = np.exp((-gamma * distances ** 2) / distances.std())
#similitudes[similitudes < .98] = 0

###
        
data = open("usersAndSubs.txt")
lines = data.read().splitlines()

subs = []
count = 0
for l in lines:
    l = l.split()
    submission = int(l[2][1:])
    subs.append((l[1],veredicts_dict.get(submission, 'timelimit')))
    if subs[-1][-1] is None:
        count += 1
    
print subs

####

unique_subs = {}

for k in xrange(len(subs)):
    unique_subs[subs[k][0]] = k

indices = sorted(unique_subs.values())
distances = distances[indices][:,indices] 
similitudes = similitudes[indices][:,indices]\

print indices

              
#####
tsne_coords = sklManifold.MDS(2, dissimilarity = 'precomputed', random_state=0).fit_transform(distances)

method = sklCluster.SpectralClustering

cs = np.arange(2, similitudes.shape[0], 5)

scores = np.zeros(cs.shape)
for j, c in enumerate(cs):
    for k in range(5):
        kmeans = method(n_clusters = c)
        l = kmeans.fit_predict(tsne_coords)
        scores[j] += metrics.silhouette_score(distances, l, metric='precomputed')
    scores[j] /= 5
        
best_score = np.max(scores)
print np.argmax(scores), scores
best_c = cs[np.argmax(scores)]
    
kmeans = method(n_clusters = best_c)
l = kmeans.fit_predict(tsne_coords)
clusters = l
print clusters

plt.plot(cs, scores)
print best_score, best_c

se = sklManifold.MDS(2, dissimilarity = 'precomputed', random_state=0).fit_transform(distances)

edges = []
treshold = 0.95
for k in range (len(similitudes[0])):
    for j in range (len(similitudes[0])):
        if similitudes[k][j] >= treshold and k != j :
            edges.append((k,j))

G = nx.Graph()
G.add_nodes_from(range(len(similitudes)))
G.add_edges_from(edges)

print G.adj.keys()
#labels = [str(k)  for k in G.adj.keys()]
labels = dict([(k, '{} {}'.format(*subs[indices[k]])) for k in G.adj.keys()])
#labels = [str(k) +","+ (str(users[k][1])) for k in range (len(users))]
print "LABELS"
print labels

G = nx.Graph()
G.add_nodes_from(range(len(similitudes)))
G.add_edges_from(edges)

pos = nx.random_layout(G)
se_dict = dict([(k, se[k])  for k in G.adj.keys()])

traceE = scatter_edges(G, se_dict, similitudes)

traceN = scatter_nodes(se_dict, clusters = clusters,labels = labels)

data1=Data(traceE + [traceN])

#print traceE

figsimilitud = Figure(data=data1, layout=layout)

figsimilitud['layout'].update(annotations = make_annotations(se_dict, [str(indices[k]) for k in G.adj.keys()]))

fignueva = Figure(data = data1, layout = layout)

plotly.offline.plot(figsimilitud)


