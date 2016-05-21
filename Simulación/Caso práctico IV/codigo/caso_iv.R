##############################################################
# Cuestión 1
##############################################################


# Generamos una simulación del coste
generateCost =  function(){
  
  # Asiganamos los parámetros
  mu = 9
  sigma = 1
  lambda_3 = 1/8
  lambda_4 = 1/16
  
  # Simulamos cada variables aleatoria del coste
  C1 = rnorm(1, mean = mu, sd = sigma)
  
  lambda_2 = 5*C1
  
  C2 = rpois(1, lambda = lambda_2)
  
  C3 = rexp(1, rate = lambda_3)
  
  C4 = rexp(1, rate = lambda_4)
  
  # Calculamos el coste total
  C = C1*C2 + C3*C4

  return(C)
}

##############################################################
# Cuestión 2
##############################################################

estimateSampleVariance =  function(alpha = 0.05, d = 10){
  i = 1
  n = array()
  n[i] = 30
  
  # Guardamos las muestras del coste
  X = array()
  for(j in 1:n[1]){
    X[j] = generateCost()
  }
  
  S = array()
  S[i] = var(X)
  
  while (2*qnorm(1-alpha/2)*S/sqrt(n[i]) > d){
    i = i + 1
    
    min_i = which(n >= (2*qnorm(1-alpha/2)*S[i-1]/d)^2)
    if(is.integer(min_i) && length(min_i) == 0){
      min_i = (2*qnorm(1-alpha/2)*S[i-1]/d)^2
      n[i] = min_i
    }else{
      n[i] = n[min_i]
    }
        
    for(j in (n[i-1] +1):n[i]){
      X[j] = generateCost()
    }
    
    S[i] = var(X)
  }
  
  return(X)
}

##############################################################
# Cuestión 3
##############################################################

hist(X, col="blue", main = "Histograma de X")