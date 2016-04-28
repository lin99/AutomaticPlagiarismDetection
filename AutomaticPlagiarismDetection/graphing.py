
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
                text = str(k), 
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



data = open("users.txt")
lines = data.read().splitlines()
print lines
users = []
for l in lines:
    users.append((l[0:2],l[2:]))

distances = np.loadtxt("distances.txt")
gamma = 0.1

similitudes = np.exp((-gamma * distances ** 2) / distances.std())
similitudes[similitudes < .95] = 0

n_neigborgs = 10
n_components = 2
n_clusters = 4

sc = sklCluster.SpectralClustering(n_clusters = 4,affinity='precomputed')

sc.fit(similitudes)

print sc.labels_

kmeans = KMeans(n_clusters=10)
clusters = kmeans.fit_predict(similitudes)

clusters = np.array(clusters)

se = sklManifold.Isomap(n_neigborgs, n_components).fit_transform(similitudes)

edges = []
treshold = 0.95
for k in range (len(similitudes[0])):
    for j in range (len(similitudes[0])):
        if similitudes[k][j] >= treshold and k != j :
            edges.append((k,j))
G = nx.Graph()
G.add_edges_from(edges)

labels = dict([(k, str(users[k][1])) for k in G.adj.keys()])


G = nx.Graph()
G.add_edges_from(edges)

pos = nx.random_layout(G)
se_dict = dict([(k, se[k])  for k in G.adj.keys()])

traceE = scatter_edges(G, se_dict, similitudes)

traceN = scatter_nodes(se_dict, clusters = clusters,labels = labels)

data1=Data(traceE + [traceN])

#print traceE

figsimilitud = Figure(data=data1, layout=layout)

figsimilitud['layout'].update(annotations = make_annotations(se_dict, [str(k) for k in G.adj.keys()]))

fignueva = Figure(data = data1, layout = layout)


plotly.offline.plot(figsimilitud)    
