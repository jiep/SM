##############################################################
# Cuestión 1
##############################################################

# Matriz de transición de la cadena de Markov
P = matrix(c(0.1, 0.4, 0.5, 0.1, 0.7, 0.2, 0.3, 0.1, 0.6),
           nrow = 3, 
           ncol = 3,
           byrow = TRUE)


# Nombre de los estados
states = c("estado0", "estado1", "estado2")

# Asignamos los estados a la matriz de transición
dimnames(P) <- list(states, states)


# Definimos una función para muestrear una cadena de Markov
samplingCMTD = function(P, N, i0){
  X = array()
  X[1] = i0
  i = 2
  while(i < N){
    h = rgeom(1, 1-P[i0,i0])
    cat("h:", h, "\n i:",i, "\n", "i+h:", i+h, "\n")
    X[i+h] = generateDist(P, i0, colnames(P))
    cat("estado: ", X[i+h], "\n", "---------------\n")
    i0 = X[i+h]
    i = i + 1
  }
  #X = X[!is.na(X)]
  return(X)
}

# Definimos una función para generar la distribución discreta 
generateDist = function(P, state, states){
  
  # Eliminamos el estado en el que nos encontramos
  states = setdiff(states, state)
  
  # Vector de probabilidades vacío
  probs = array(dim = length(states))
  
  # Recorremos el vector de estados 
  # y asignamos la probabilidad
  for(i in 1:length(states)){
    probs[i] = P[state, states[i]]/(1-P[state, state])
  }
  
  names(probs) = states
  
  u = runif(1)

  # Calculamos el vector acumulado 
  # y añadimos 0 al comienzo
  cumulative = unname(c(0,cumsum(probs)))
  
  # Calculamos la longitud real del vector (le hemos añadido una posición nueva)
  n = length(cumulative) - 1
  
  # Buscamos la posición que acumula la cantidad de probabilidad
  # generada por el número aleatorio y lo asignamos al estado.
  for(i in 1:n){
    if((cumulative[i] <= u) & (u <= cumulative[i+1])){
      stat = states[i]
      break
    }
  }
  
  return(stat)
  
}

##############################################################
# Cuestión 2
##############################################################

# Número de muestras de cada réplica
N = 1000

# Número de réplicas
n = 100

X = list()
for(i in 1:n){
  a = samplingCMTD(P, N, "estado0")
  X[[i]] = summary(as.factor(na.omit(a)))/length(na.omit(a))
}

# Guardamos la media de las proporciones de cada estado
state1 = array()
state2 = array()
state3 = array()

for(i in 1:n){
  state1[i] = X[[i]][[1]]
  state2[i] = X[[i]][[2]]
  state3[i] = X[[i]][[3]]
}

# Media y desviación típica de cada estado
mean(state1)
sd(state1)

mean(state2)
sd(state2)

mean(state3)
sd(state3)
