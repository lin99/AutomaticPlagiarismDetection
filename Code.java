

package A;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;








public class PA<T> 

{
    private NodoPA<T> raiz;

    private static int peso;


    public PA( )
    {
        raiz = new NodoPA<T>( ' ' );
    }

    public PA( NodoPA<T> r, int p )
    {
        raiz = r;
        peso = p;
    }

   
    
    public static class ListaEncadenada<T> 
    {
       
        private NodoLista<T> primero;

        
        private NodoLista<T> ultimo;

        
        private int numElems;

      
        public ListaEncadenada( )
        {
            primero = null;
            ultimo = null;
            numElems = 0;
        }

        public static class NoExisteException extends Exception
        {
            
            public NoExisteException( String mensaje )
            {
                super( mensaje );
            }
        }

        public T buscar( T modelo )
        {
            for( NodoLista<T> p = primero; p != null; p = p.darSiguiente( ) )
            {
                if( p.darElemento( ).equals( modelo ) )
                {
                    return p.darElemento( );
                }
            }
            return null;
        }

       
        public int darLongitud( )
        {
            return numElems;
        }

       
        public NodoLista<T> darPrimero( )
        {
            return primero;
        }

        public NodoLista<T> darUltimo( )
        {
            return ultimo;
        }

    
        public void insertarCabeza( T elemento )
        {
            NodoLista<T> nodo = new NodoLista<T>( elemento );
            if( primero == null )
            {
                primero = nodo;
                ultimo = nodo;
            }
            else
            {
                primero.insertarAntes( nodo );
                primero = nodo;
            }
            numElems++;
        }

      
        public void insertarCola( T elemento )
        {
            NodoLista<T> nodo = new NodoLista<T>( elemento );
            if( primero == null )
            {
                primero = nodo;
                ultimo = nodo;
            }
            else
            {
                ultimo.insertarDespues( nodo );
                ultimo = nodo;
            }
            numElems++;
        }

        public void eliminarNodo( NodoLista<T> nodo ) throws NoExisteException
        {
            if( buscar( nodo.darElemento( ) ) != null )
            {
                throw new NoExisteException( "El nodo especificado no pertenece a la lista" );
            }
            if( primero == nodo )
            {
                primero = nodo.desconectarPrimero( );
                if( ultimo == nodo )
                {
                    ultimo = null;
                }
            }
            else
            {
                if( ultimo == nodo )
                {
                    ultimo = nodo.darAnterior( );
                }
                nodo.desconectarNodo( );
            }
        }

        public T eliminarPrimero( )
        {
            //
            // Si no tiene
            if( primero == null )
            {
                return null;
            }
            else
            {
                //
                // Elimina el primer elemento
                try
                {
                    return eliminar( primero.darElemento( ) );
                }
                catch( NoExisteException e )
                {
                    
                    return null;
                }
            }
        }

        public T eliminarUltimo( )
        {
           
            if( ultimo == null )
            {
                return null;
            }
            else
            {
                
                try
                {
                    return eliminar( ultimo.darElemento( ) );
                }
                catch( NoExisteException e )
                {
                    
                    return null;
                }
            }
        }

       
        public T eliminar( T elemento ) throws NoExisteException
        {
            T valor = null;
            if( primero == null )
            {
                throw new NoExisteException( "Elemento no existe" );
            }
            else if( primero.darElemento( ).equals( elemento ) )
            {
                if( primero.equals( ultimo ) )
                {
                    ultimo = null;
                }
                valor = primero.darElemento( );
                primero = primero.desconectarPrimero( );
                numElems--;
                return valor;
            }
            else
            {
                for( NodoLista<T> p = primero.darSiguiente( ); p != null; p = p.darSiguiente( ) )
                {
                    if( p.darElemento( ).equals( elemento ) )
                    {
                        if( p.equals( ultimo ) )
                        {
                            ultimo = p.darAnterior( );
                        }
                        valor = p.darElemento( );
                        p.desconectarNodo( );
                        numElems--;
                        return valor;
                    }
                }
                throw new NoExisteException( "Elemento no existe" );
            }
        }

         
        public void insertar( T elemento, int pos )
        {
            NodoLista<T> nodo = new NodoLista<T>( elemento );
            if( ( pos >= numElems ) || pos < 0 )
            {
                throw new IndiceFueraDeRangoException( pos );
            }
            else
            {
                NodoLista<T> aux = primero;

                for( int cont = 0; cont < pos - 1; cont++ )
                {
                    aux = aux.darSiguiente( );
                }
                aux.insertarDespues( nodo );
                numElems++;
            }
        }

      
        public T dar( int pos )
        {
            if( pos >= numElems || pos < 0 )
            {
                throw new IndiceFueraDeRangoException( pos );
            }
            else
            {
                NodoLista<T> aux = primero;

                for( int cont = 0; cont < pos; cont++ )
                {
                    aux = aux.darSiguiente( );
                }

                return aux.darElemento( );
            }
        }

    
        public T eliminar( int pos )
        {
            T valor = null;

            if( ( pos >= numElems ) || pos < 0 )
            {
                throw new IndiceFueraDeRangoException( pos );
            }
            else if( pos == 0 )
            {
                if( primero.equals( ultimo ) )
                {
                    ultimo = null;
                }
                valor = primero.darElemento( );
                primero = primero.desconectarPrimero( );
                numElems--;
                return valor;
            }
            else
            {

                NodoLista<T> p = primero.darSiguiente( );
                for( int cont = 1; cont < pos; cont++ )
                {
                    p = p.darSiguiente( );
                }

                if( p.equals( ultimo ) )
                {
                    ultimo = p.darAnterior( );
                }
                valor = p.darElemento( );
                p.desconectarNodo( );
                numElems--;
                return valor;
            }
        }

        
        public boolean contiene( T modelo )
        {
            return buscar( modelo ) != null;
        }

       
        public void vaciar( )
        {
            primero = null;
            ultimo = null;
            numElems = 0;
        }

         
        public Iterador<T> darIterador( )
        {
            IteradorSimple<T> respuesta = new IteradorSimple<T>( numElems );
            NodoLista<T> iterador = primero;
            while( iterador != null )
            {
                try
                {
                    respuesta.agregar( iterador.darElemento( ) );
                    iterador = iterador.darSiguiente( );
                }
                catch( IteradorException e )
                {
                     
                }
            }
            return respuesta;
        }

    
        public String toString( )
        {
            String resp = "ida: [" + numElems + "]:";
            for( NodoLista<T> p = primero; p != null; p = p.darSiguiente( ) )
            {
                resp += p.darElemento( ).toString( ) + "<->";
            }
            resp += "\r\nvuelta:[" + numElems + "]:";
            for( NodoLista<T> p = ultimo; p != null; p = p.darAnterior( ) )
            {
                resp += p.darElemento( ).toString( ) + "<->";
            }
            return resp;
        }
        
        
        public IteradorSinMemoria<T> darIteradorSinMemoria()
        {
            IteradorSinMemoria<T> it = new IteradorSinMemoria<T>(primero);
            return it;      
        }
    }
    
   
    public static  class IteradorSinMemoria<T> implements Iterador<T>
    {
         
        INodo<T> nodoActual;
 
        INodo<T> primero;

         
        INodo<T> ultimo;
 
        public IteradorSinMemoria( INodo<T> nNodo )
        {
            nodoActual = nNodo;
            primero = nNodo;
            ultimo = null;
        }
 
        public T darAnterior( )
        {
            T elemento = null;

            if( hayAnterior( ) )
            {
                if( nodoActual != null )
                {
                    nodoActual = nodoActual.darAnterior( );
                    elemento = nodoActual.darElemento( );
                }
                else if( ultimo != null )
                {
                    nodoActual = ultimo;
                    elemento = nodoActual.darElemento( );
                }

            }

            return elemento;
        }

        
        public T darSiguiente( )
        {
            T elemento = null;

            if( haySiguiente( ) )
            {
                elemento = nodoActual.darElemento( );
                if( nodoActual.darSiguiente( ) == null )
                    ultimo = nodoActual;
                nodoActual = nodoActual.darSiguiente( );
            }

            return elemento;
        }

       
        public boolean hayAnterior( )
        {
            boolean hayAnterior = false;

            if( nodoActual != null )
                hayAnterior = nodoActual.darAnterior( ) != null;
            else if( ultimo != null )
                hayAnterior = true;

            return hayAnterior;
        }

       
        public boolean haySiguiente( )
        {
            return nodoActual != null;
        }
 
        public void reiniciar( )
        {
            nodoActual = primero;
            ultimo = null;
        }
    }
   
    public static  class NodoPA<T> 
    {
        
        private char caracter;
 
        private T elemento;

         
        private NodoPA<T> izqNodo;

       
        private NodoPA<T> hermanoDerNodo;

        
        public NodoPA( char carac )
        {
            caracter = carac;
            izqNodo = null;
            hermanoDerNodo = null;
            elemento = null;
        }

         
        public NodoPA( char carac, T elem )
        {
            caracter = carac;
            izqNodo = null;
            hermanoDerNodo = null;
            elemento = elem;
        }
 
        public int insertar( String palabra, T elem ) 
        {

            // Si la palabra solo tiene 1 carácter y el elemento del hijo izquierdo es igual al elemento a insertar, entonces la palabra es repetida
            if( palabra.length( ) == 1 && izqNodo != null && izqNodo.elemento != null && izqNodo.elemento.equals( elem ) )
            {
                
            }

            int elementosInsertados = 0;
            char nCaracter = palabra.charAt( 0 );

            if( izqNodo != null && izqNodo.caracter == nCaracter )// Si el carácter del hijo izquierdo es igual al que se quiere insertar
            {
                if( palabra.length( ) != 1 )
                    return izqNodo.insertar( palabra.substring( 1 ), elem );// El hijo izquierdo es responsable de insertar el nuevo elemento
                else
                    izqNodo.elemento = elem;// Se agregó un prefijo de una palabra existente
            }
            else if( izqNodo != null && izqNodo.caracter > nCaracter )
            {
                NodoPA<T> nuevoNodo = null;
                if( palabra.length( ) == 1 )
                {
                    nuevoNodo = new NodoPA<T>( nCaracter, elem );
                }
                else
                {
                    nuevoNodo = new NodoPA<T>( nCaracter );// Nodo solo con el caracter
                    elementosInsertados += nuevoNodo.insertar( palabra.substring( 1 ), elem );
                }
                // El carácter a insertar debería ser el nuevo hijo izquierdo del nodo actual
                NodoPA<T> temp = izqNodo;
                izqNodo = nuevoNodo;
                izqNodo.hermanoDerNodo = temp;
                elementosInsertados++;
            }
            else if( izqNodo != null && izqNodo.hermanoDerNodo != null )// Se debe agregar el nodo sobre los hermanos del nodo actual
            {
                // Se mantiene el anterior por si se deben correr los nodos hermanos
                NodoPA<T> iterador = izqNodo.hermanoDerNodo;
                NodoPA<T> anterior = izqNodo;
                // Ubicar el lugar donde deba ir el nuevo hermano
                while( iterador != null && iterador.caracter < nCaracter )
                {
                    anterior = iterador;
                    iterador = iterador.hermanoDerNodo;
                }
                if( iterador != null && iterador.caracter == nCaracter )// Encontró el nodo que deba agregar el resto de la palabra
                {
                    if( palabra.length( ) == 1 )
                    {
                        // En caso que la palabra termine y solo deba agregar el elemento a ese nodo
                        iterador.elemento = elem;
                        elementosInsertados++;
                    }
                    else
                        elementosInsertados += iterador.insertar( palabra.substring( 1 ), elem );
                }
                else
                {
                    NodoPA<T> nuevoNodo = null;
                    if( palabra.length( ) == 1 )
                    {
                        nuevoNodo = new NodoPA<T>( nCaracter, elem );
                    }
                    else
                    {
                        nuevoNodo = new NodoPA<T>( nCaracter );// Nodo solo con el caracter
                        elementosInsertados += nuevoNodo.insertar( palabra.substring( 1 ), elem );
                    }
                    // Se debe insertar un nuevo nodo entre dos hermanos
                    // Debe crear un nuevo nodo y colocarlo
                    anterior.hermanoDerNodo = nuevoNodo;// El nuevo hermanoDerecho del anterior es el nuevo nodo
                    nuevoNodo.hermanoDerNodo = iterador;// El hermanoDerecho del nuevo nodo es el nodo que se tenia en iterador
                    elementosInsertados++;
                }
            }
            else if( izqNodo != null && izqNodo.hermanoDerNodo == null )// No existe un hermano del nodo izquierdo
            {
                NodoPA<T> nuevoNodo = null;
                if( palabra.length( ) == 1 )
                {
                    nuevoNodo = new NodoPA<T>( nCaracter, elem );
                }
                else
                {
                    nuevoNodo = new NodoPA<T>( nCaracter );// Nodo solo con el caracter
                    elementosInsertados += nuevoNodo.insertar( palabra.substring( 1 ), elem );
                }
                izqNodo.hermanoDerNodo = nuevoNodo;
                elementosInsertados++;
            }
            else
            {
                NodoPA<T> nuevoNodo = null;
                if( palabra.length( ) == 1 )
                {
                    nuevoNodo = new NodoPA<T>( nCaracter, elem );
                }
                else
                {
                    nuevoNodo = new NodoPA<T>( nCaracter );// Nodo solo con el caracter
                    elementosInsertados += nuevoNodo.insertar( palabra.substring( 1 ), elem );
                }
                // Se agrega un nodo como el hijo izquierdo del nodo actual
                izqNodo = nuevoNodo;
                elementosInsertados++;
            }
            return elementosInsertados;
        }
 
        public int eliminar( String palabra )
        {
            int eliminados = 0;
            char c = palabra.charAt( 0 ); // Primer carácter de la palabra

            if( izqNodo != null && izqNodo.caracter == c ) // Sólo se continúa la eliminación si el primer carácter de la palabra es igual al del nodo actual
            {
                if( palabra.length( ) > 1 )
                {
                    eliminados += izqNodo.eliminar( palabra.substring( 1 ) );
                    if( izqNodo.izqNodo == null && eliminados > 0 && izqNodo.elemento == null )
                    {
                        izqNodo = izqNodo.hermanoDerNodo;
                        eliminados++;
                    }
                }
                else
                {
                    if( izqNodo.elemento != null )
                    {
                        izqNodo.elemento = null;
                        if( izqNodo.izqNodo == null )
                        {
                            izqNodo = izqNodo.hermanoDerNodo;
                            eliminados++;
                        }
                    }
                }
            }
            else if( izqNodo != null && izqNodo.caracter < c && izqNodo.hermanoDerNodo != null ) // Buscar si la palabra existe en otro nodo a la derecha y eliminarla
            {
                // Buscar el nodo en el que se debe realizar la eliminación
                NodoPA<T> hermanoIzquierdo = izqNodo;
                NodoPA<T> nodo = izqNodo.hermanoDerNodo;
                while( nodo != null && nodo.caracter != c )
                {
                    hermanoIzquierdo = nodo;
                    nodo = nodo.hermanoDerNodo;
                }

                if( nodo != null && palabra.length( ) > 1 )
                {
                    eliminados = nodo.eliminar( palabra.substring( 1 ) );
                    if( nodo.izqNodo == null && eliminados > 0 && nodo.elemento == null )
                    {
                        hermanoIzquierdo.hermanoDerNodo = nodo.hermanoDerNodo;
                        eliminados++;
                    }
                }
                else if( nodo != null && nodo.elemento != null )
                {
                    nodo.elemento = null;
                    if( nodo.izqNodo == null )
                    {
                        hermanoIzquierdo.hermanoDerNodo = nodo.hermanoDerNodo;
                        eliminados++;
                    }
                }
            }
            return eliminados;
        }

        
        public T buscar( String palabra )
        {
            T retorno = null;
            NodoPA<T> aux = this.izqNodo;
            while( aux != null && palabra.length( ) != 0 )
            {
                char nCaracter = palabra.charAt( 0 );
                if( aux.caracter == nCaracter )
                {
                    palabra = palabra.substring( 1 );
                    if( palabra.length( ) == 0 )
                    {
                        retorno = aux.elemento;
                    }
                    else
                    {
                        aux = aux.izqNodo;
                    }
                }
                else if( aux.hermanoDerNodo != null )
                {
                    while( aux != null && aux.caracter != nCaracter )
                    {
                        aux = aux.hermanoDerNodo;
                    }
                }
                else
                    aux = null;
            }
            return retorno;
        }

       
        public int darAltura( )
        {
            int altura = ( izqNodo == null ) ? 1 : izqNodo.darAltura( ) + 1;
            int aux = ( hermanoDerNodo == null ) ? 0 : hermanoDerNodo.darAltura( );

            if( aux > altura )
            {
                altura = aux;
            }
            return altura;
        }

        
        public NodoPA<T> darHijoIzquierdo( )
        {
            return izqNodo;
        }
 
        public NodoPA<T> darHermanoDerecho( )
        {
            return hermanoDerNodo;
        }
 
        public char darCaracter( )
        {
            return caracter;
        }

        
        public T darElemento( )
        {
            return elemento;
        }
 
        public void buscarPorPrefijo( String prefijo, Lista<T> lista )
        {
            NodoPA<T> aux = this.izqNodo;
            // Se ubica el nodo a partir del cual se deben recolectar los elementos
            while( aux != null && prefijo.length( ) != 0 )
            {
                char nCaracter = prefijo.charAt( 0 );
                if( aux.caracter == nCaracter )
                {
                    prefijo = prefijo.substring( 1 );
                    if( prefijo.length( ) != 0 )
                    {
                        aux = aux.izqNodo;
                    }
                }
                else if( aux.hermanoDerNodo != null )
                {
                    while( aux != null && aux.caracter != nCaracter )
                    {
                        aux = aux.hermanoDerNodo;
                    }
                }
                else
                    aux = null;
            }
            if( aux != null )
            {
                aux.darElementosPA( lista );
            }

        }

       
        private void darElementosPA( Lista<T> lista )
        {
            if( elemento != null )
            {
                lista.agregar( elemento );
            }

            if( izqNodo != null )
            {
                izqNodo.darElementosPA( lista );
                NodoPA<T> nodo = izqNodo.hermanoDerNodo;

                while( nodo != null )
                {
                    nodo.darElementosPA( lista );
                    nodo = nodo.hermanoDerNodo;
                }
            }
        }

       
        public void inorden( IteradorSimple<T> resultado )
        {

            if( izqNodo != null )
            {
                izqNodo.inorden( resultado );
                if( elemento != null )
                {
                    try
                    {
                        resultado.agregar( elemento );
                    }
                    catch( IteradorException e )
                    {
                         
                    }
                }
                NodoPA<T> aux = izqNodo.hermanoDerNodo;
                while( aux != null )
                {
                    aux.inorden( resultado );
                    aux = aux.hermanoDerNodo;
                }
            }
            else
            {
                if( elemento != null )
                {
                    try
                    {
                        resultado.agregar( elemento );
                    }
                    catch( IteradorException e )
                    {
                         
                    }
                }
            }
        }
    }

    
   
    public static class Lista<T> 
    {
        
        private final static int INIT = 20;
 
        private final static int DELTA = 20;

        
        protected T[] elems;
 
        protected int numElems;
 
        public Lista( )
        {
            this( INIT );
        }

        
        public Lista( int capacidad )
        {
            elems = ( T[] )new Object[capacidad];
            numElems = 0;
        }

         

        public T darElemento( int pos )
        {
            if( pos < 0 || pos > numElems )
                throw new IndiceFueraDeRangoException( pos );
            return elems[ pos ];
        }

        
        public void agregar( T elem )
        {
            // Verifica si hay que aumentar el tamaño de la representación
            if( numElems == elems.length )
            {
                T viejo[] = elems;
                elems = ( T[] )new Object[elems.length + DELTA];
                System.arraycopy( viejo, 0, elems, 0, viejo.length );
            }
            elems[ numElems++ ] = elem;
        }

        
        public void insertar( T elem, int pos )
        {
            if( pos < 0 || pos > numElems )
            {
                throw new IndiceFueraDeRangoException( pos );
            }
            // Verifica si hay que aumentar el tamaño de la representación
            if( numElems == elems.length )
            {
                T viejo[] = elems;
                elems = ( T[] )new Object[elems.length + DELTA];
                System.arraycopy( viejo, 0, elems, 0, viejo.length );
            }
            // Abre espacio para el nuevo elemento
            for( int i = numElems - 1; i >= pos; i-- )
            {
                elems[ i + 1 ] = elems[ i ];
            }
            // Incrementa el número de elementos
            numElems++;
            // Inserta el nuevo elemento
            elems[ pos ] = elem;
        }

       
        public T eliminar( int pos )
        {
            if( pos < 0 || pos >= numElems )
            {
                throw new IndiceFueraDeRangoException( pos );
            }
            // Saca el elemento a eliminar para ser retornado.
            T resp = elems[ pos ];
            // Desplaza los elementos
            for( int i = pos; i < numElems - 1; i++ )
            {
                elems[ i ] = elems[ i + 1 ];
            }
            // Se vuelve null el último elemento
            elems[ numElems - 1 ] = null;
            // Se reduce la cantidad de elementos
            numElems--;
            // Se retorna el elemento eliminado
            return resp;
        }
 
        public T eliminar( T elem )
        {
            int pos = 0;
            // Se busca la posición del elemento a eliminar
            for( ; pos < numElems && !elem.equals( elems[ pos ] ); pos++ )
                ;

            T eliminado = null;
            // Si se encuentra la posición y es menor que la cantidad de elementos se llama el método eliminar por posición
            if( pos < numElems )
                eliminado = eliminar( pos );
            // Se retorna el elemento eliminado o null en caso que no exista
            return eliminado;
        }
 
        public int buscar( T elem )
        {
            int pos = 0;
            for( ; pos < numElems && !elem.equals( elems[ pos ] ); pos++ )
                ;
            return pos == numElems ? -1 : pos;
        }
 
        public int darLongitud( )
        {
            return numElems;
        }
 
        public void asignar( T elem, int pos )
        {
            if( pos < 0 || pos > numElems || numElems == 0 )
                throw new IndiceFueraDeRangoException( pos );
            elems[ pos ] = elem;
        }
 
        public Iterador<T> darIterador( )
        {
            IteradorSimple<T> respuesta = new IteradorSimple<T>( numElems );
            for( int i = 0; i < numElems; i++ )
            {
                try
                {
                    respuesta.agregar( elems[ i ] );
                }
                catch( IteradorException e )
                {
                    // Nunca debería ocurrir esta excepción
                }
            }
            return respuesta;
        }

        
        public void vaciar( )
        {
            // Se borran las referencias a los objetos presentes en la lista, para permitir el adecuado
            // trabajo del recolector de basura
            for( int i = 0; i < numElems; i++ )
                elems[ i ] = null;
            numElems = 0;
        }
 
        public boolean contiene( T elem )
        {
            return buscar( elem ) != -1;
        }
 
        public boolean esVacio( )
        {
            return darLongitud( ) == 0;
        }
 
        public String toString( )
        {
            String resp = "[" + numElems + "]:";
            for( int i = 0; i < numElems; i++ )
            {
                resp += elems[ i ] + "-";
            }
            return resp;
        }
    }
    
    
    public static  class IteradorSimple<T> implements Iterador<T>
    {
         
        private final static int NADA = -1;
 
        private T[] elems;
 
        private int posActual;
 
        private int sigPosLibre;

         
        public IteradorSimple( int tamanio )
        {
            elems = ( T[] )new Object[tamanio];
            sigPosLibre = 0;
            posActual = NADA;
        }

         

        public boolean haySiguiente( )
        {
            return elems.length > 0 && ( posActual + 1 ) < sigPosLibre;
        }
 
        public T darSiguiente( )
        {
            return haySiguiente( ) ? elems[ ++posActual ] : null;
        }

        
        public T darAnterior( )
        {
            return hayAnterior( ) ? elems[ --posActual ] : null;
        }

         
        public boolean hayAnterior( )
        {
            return elems.length > 0 && posActual > 0;
        }

        
        public void reiniciar( )
        {
            posActual = NADA;
        }

        
        public void agregar( T elem ) throws IteradorException
        {
            if( sigPosLibre <= elems.length - 1 )
            {
                elems[ sigPosLibre++ ] = elem;
            }
            else
                throw new IteradorException( "Límite del iterador alcanzado" );
        }

        
        public void insertar( T elem ) throws IteradorException
        {
            if( sigPosLibre >= elems.length )
                throw new IteradorException( "Límite del iterador alcanzado" );
            // Abre espacio para el nuevo elemento
            for( int i = sigPosLibre; i > 0; i-- )
            {
                elems[ i ] = elems[ i - 1 ];
            }
            sigPosLibre++;
            elems[ 0 ] = elem;
        }
 
        public String toString( )
        {
            String resp = "[" + sigPosLibre + "]:";
            for( int i = 0; i < sigPosLibre; i++ )
            {
                resp += elems[ i ] + "-";
            }
            return resp;
        }

        
        public int darSigPosLibre( )
        {
            return sigPosLibre;
        }

        
        public int darPosActual( )
        {
            return posActual;
        }
 
        public int darLongitud( )
        {
            return elems.length;
        }
    }
    public static  class IteradorException extends Exception
    { 
        public IteradorException( String mensaje )
        {
            super( mensaje );
        }
    }
    public static class IteradorFlexible<T> extends ListaEncadenada<T> implements Iterador<T>
    {
        
        private final static int NADA = -1;
 
        private int posActual;
 
        public IteradorFlexible( )
        {
            super( );
            posActual = NADA;
        }
 
        public boolean haySiguiente( )
        {
            return ( posActual + 1 ) < darLongitud( );
        }
 
        public T darSiguiente( )
        {
            return haySiguiente( ) ? dar( ++posActual ) : null;
        }
       
        public T darAnterior( )
        {
            return hayAnterior( ) ? dar(--posActual) : null;
        }

        
        public boolean hayAnterior( )
        {
            return posActual > 0;
        }


        
        public void reiniciar( )
        {
            posActual = NADA;
        }

         
        public int darPosActual( )
        {
            return posActual;
        }

       
        public void agregar( T elem )
        {
            insertarCola( elem );
        }

        
        public void insertar( T elem )
        {
            insertarCabeza( elem );
        }
 
        public String toString( )
        {
            String resp = "[" + darLongitud( ) + "]:";
            for( int i = 0; i < darLongitud( ); i++ )
            {
                resp += dar( i ) + "-";
            }
            return resp;
        }
    }
    
    
    public void insertar( T elemento ) 
    {
        String palabra = elemento.toString( );
       
            peso += raiz.insertar( palabra, elemento );
        
    }
 
    public T eliminar( String palabra )  
    {
        
        T elemento = buscar( palabra );
      
        int eliminados = elemento != null ? raiz.eliminar( palabra ) : 0;
        peso -= eliminados;
        return elemento;
    }

    
    public T buscar( String palabra )
    {
        return raiz.buscar( palabra );
    }
 
    public Lista<T> buscarPorPrefijo( String prefijo )
    {
        Lista<T> lista = new Lista<T>( );

        raiz.buscarPorPrefijo( prefijo, lista );

        return lista;
    }

   
    public IteradorSimple<T> inorden( )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( peso );
        raiz.inorden( resultado );

        return resultado;
    }

    
    public int darPeso( )
    {
        return peso;
    }

     
    public int darAltura( )
    {
        return ( raiz != null ) ? raiz.darAltura( ) : 0;
    }
 
    public NodoPA<T> darRaiz( )
    {
        return raiz;
    }
    
    public static void main( String [ ] args ) throws FileNotFoundException{
        
        
        
        File f = new File("B_1.txt");
        Scanner scan;
        int encontrado8=0;;
        if (f.exists()) {
        scan = new Scanner(f);
        } else {
            scan = new Scanner(System.in);
        }
        
         String lines2[] = scan.nextLine().split(" ");//leer linea completa
        int t = Integer.parseInt(lines2[0]);//numero de casos de prueba

        for (int test = 0; test < t; test++) {
             encontrado8++;
            System.out.println("Case #"+encontrado8+":");
             
            
            
             String lines4[] = scan.nextLine().split(" ");//leer linea completa
                int c = Integer.parseInt(lines4[0]);//numero de casos de prueba
                PA rer= new PA<String>( );
             for(int x=0;x<c;x++){
                
                 String lines[] = scan.nextLine().split(" ");//leer linea completa
                
                menu(rer,lines);
                
             }
            
        }
         
        
        
    }
    public static void menu(PA rr,String[] lines) {

        switch(lines[0]){
        case "agregar":
            
            rr.insertar(lines[1]);
            
            break;
        case "buscar":
            if(rr.buscarPorPrefijo(lines[1]).darLongitud()==0){
                System.out.println("no");
                
            }else{
                System.out.println("yes");
            }
            
            
            break;
        case "sugerir":
            //String aux=rr.buscarPorPrefijo(lines[1]);
            
            System.out.println(rr.buscarPorPrefijo(lines[1]).darLongitud());    
            break;
        
    
    }
    
}
    
    public static class IndiceFueraDeRangoException extends RuntimeException
    {
         
        public IndiceFueraDeRangoException( int valor )
        {
            super( "Índice: " + valor );
        }
    }
    public static class NodoLista<T> implements INodo<T>
    {
         
        
        private T elemento;
     
        private NodoLista<T> sigNodo;
     
        private NodoLista<T> antNodo;

         
        public NodoLista( T pElemento )
        {
            elemento = pElemento;
            sigNodo = null;
            antNodo = null;
        }
     
        public T darElemento( )
        {
            return elemento;
        }

         
        public NodoLista<T> darSiguiente( )
        {
            return sigNodo;
        }

         
        public NodoLista<T> darAnterior( )
        {
            return antNodo;
        }

         
        public void insertarAntes( NodoLista<T> nodo )
        {
            nodo.sigNodo = this;
            nodo.antNodo = antNodo;
            if( antNodo != null )
                antNodo.sigNodo = nodo;
            antNodo = nodo;
        }
     
        public void insertarDespues( NodoLista<T> nodo )
        {
            nodo.sigNodo = sigNodo;
            nodo.antNodo = this;
            if( sigNodo != null )
                sigNodo.antNodo = nodo;
            sigNodo = nodo;
        }
     
        public NodoLista<T> desconectarPrimero( )
        {
            NodoLista<T> p = sigNodo;
            sigNodo = null;
            if( p != null )
            {
                p.antNodo = null;
            }
            return p;
        }
     
        public void desconectarNodo( )
        {
            NodoLista<T> ant = antNodo;
            NodoLista<T> sig = sigNodo;
            antNodo = null;
            sigNodo = null;
            ant.sigNodo = sig;
            if( sig != null )
            {
                sig.antNodo = ant;
            }
        }
     
        public String toString( )
        {
            return elemento.toString( );
        }
    }
    
}

