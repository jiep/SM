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
  n0 = 30
  i = 0
  
  # Guardamos las muestras del coste
  X = array()
  for(j in 1:n0){
    X[j] = generateCost()
  }
  
  S = var(X)
  
  while (2*qnorm(alpha/2)*S/sqrt(n0) > d){
    i = i + 1
    
  }
}