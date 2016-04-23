##############################################################
# Cuestión 1
##############################################################

# Número de muestras
num_samples = c(10, 100, 10000)

# Parámetros de la distribución normal
mu = 3
sigma = 6


# Guardamos las muestras de cada distrubución generada 
samples_10 = rnorm(num_samples[1], mu, sigma)
samples_100 = rnorm(num_samples[2], mu, sigma)
samples_10000 = rnorm(num_samples[3], mu, sigma)

# Construimos los histogramas
par(mfrow = c(1,3))
hist(samples_10)
hist(samples_100)
hist(samples_10000)

# Calculamos los estadísticos más relevantes

# Mínimo y máximo, primer y tercer cuartil, media y mediana
summary(samples_10)
summary(samples_100)
summary(samples_10000)

# Calculamos la desviación típica
sd(samples_10)
sd(samples_100)
sd(samples_10000)

# Calculamos la moda
# Extraído de 
# http://stackoverflow.com/questions/2547402/is-there-a-built-in-function-for-finding-the-mode
mode <- function(x) {
  ux <- unique(x)
  ux[which.max(tabulate(match(x, ux)))]
}

mode(samples_10)
mode(samples_100)
mode(samples_10000)

# Kurtosis y asimetría (es necesario el paquete `moments`)

# Si no está disponible lo instalamos
if(!require("moments")) { install.packages("moments") }

# Cargamos el paquete 
require("moments")

# Calculamos el kurtosis
kurtosis(samples_10)
kurtosis(samples_100)
kurtosis(samples_10000)

# Calculamos la asimetría
skewness(samples_10)
skewness(samples_100)
skewness(samples_10000)

##############################################################
# Cuestión 2
##############################################################

# Usamos el método de suma de 12 uniformes para generar 
# 10000 muestras de una normal (0,1)
x = array()
n = 10000
for(i in 1:n){
  unif_samples = runif(12)
  x[i] = sum(unif_samples) - 6
}

# Desplazamos la distribución generada para que tenga media 3
# y desviación típica 6
x = sigma*x + mu

mean(x)
sd(x)
kurtosis(x)
skewness(x)

# Generamos 10000 muestras de con la función `rnorm`  
x1 = rnorm(n, mu, sigma)

##############################################################
# Cuestión 3
##############################################################

# Generamos 10000 muestras de una distribución exponencial
# de parámetro lambda = 9

lambda = 9

exp_samples = array()
for(i in 1:n){
  u = runif(1)
  exp_samples[i] = -1/lambda * log(u)
}

# Dibujamos el histograma de la muestra generada
hist(exp_samples)

# Calculamos el coeficiente de variación
cv = sd(exp_samples)/mean(exp_samples)


##############################################################
# Cuestión 4
##############################################################

# Usando rnorm generamos una distribución normal bivariante

# Parámetros de la función bivariante
Mu = c(3, 2)

Sigma = matrix(c(7, 4, 4, 5), ncol = 2, nrow = 2)

# Calculamos la matriz de Cholesky de la matriz de varianzas
# y covarianzas
L = t(chol(Sigma))

# Generamos 10000 muestras de la distribución
multi_dist = matrix(nrow = 2, ncol = n)
for(i in 1:n){
  Z = rnorm(length(Mu))
  X = Mu + L%*%Z
  multi_dist[1,i] = X[1]
  multi_dist[2,i] = X[2]
}

# Calculamos las medias
apply(multi_dist, 1, mean)

# Calculamos las varianzas y desviaciones típicas
apply(multi_dist, 1, var)
apply(multi_dist, 1, sd)

# Calculamos las covarianzas
x = multi_dist[1,]
y = multi_dist[2,]

cov(x,y)


# Representamos el scatterplot

# Si no está el paquete `scatterplot3d`, lo instalamos
if(!require("scatterplot3d")) { install.packages("scatterplot3d") }

# Cargamos el paquete `scatterplot3d`
require("scatterplot3d")


