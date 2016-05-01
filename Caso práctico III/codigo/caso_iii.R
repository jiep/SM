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



samplingCMTD = function(P, N, i0){
  X = array()
  X[1] = i0
  i = 1
  while(i <= N){
    h = rgeom(1, 1-P[i0,i0])
    X[i+h] = generateDist(P, i0, colnames(P))
    i0 = X[i+h]
    i = i + 1
  }
  
  return(X)
}

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

