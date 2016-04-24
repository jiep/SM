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
hist(samples_10, main = "10 muestras", xlab = "", "ylab" = "Frecuencia", col = "blue")
hist(samples_100, main = "100 muestras", xlab = "", "ylab" = "Frecuencia", col = "blue")
hist(samples_10000, main = "10000 muestras", xlab = "", "ylab" = "Frecuencia", col = "blue")

# Restauramos la vista de los plots
par(mfrow=c(1,1))

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
# Si no está disponible el paquete, lo instalamos
if(!require("modeest")) { install.packages("modeest") }

# Cargamos el paquete 
require("modeest")

mlv(samples_10, method = "mfv")
mlv(samples_100, method = "mfv")
mlv(samples_10000, method = "mfv")

# Kurtosis y asimetría (es necesario el paquete `moments`)

# Si no está disponible, lo instalamos
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

# Generamos 10000 muestras de una distribuci?n exponencial
# de par?metro lambda = 9

lambda = 9

exp_samples = array()
for(i in 1:n){
  u = runif(1)
  exp_samples[i] = -1/lambda * log(u)
}

# Dibujamos el histograma de la muestra generada
hist(exp_samples)

# Calculamos el coeficiente de variaci?n
cv = sd(exp_samples)/mean(exp_samples)


##############################################################
# Cuesti?n 4
##############################################################

# Usando rnorm generamos una distribuci?n normal bivariante

# Par?metros de la funci?n bivariante
Mu = c(3, 2)

Sigma = matrix(c(7, 4, 4, 5), ncol = 2, nrow = 2)

# Calculamos la matriz de Cholesky de la matriz de varianzas
# y covarianzas
L = t(chol(Sigma))

# Generamos 10000 muestras de la distribuci?n
multi_dist = matrix(nrow = 2, ncol = n)
for(i in 1:n){
  Z = rnorm(length(Mu))
  X = Mu + L%*%Z
  multi_dist[1,i] = X[1]
  multi_dist[2,i] = X[2]
}

# Calculamos las medias
apply(multi_dist, 1, mean)

# Calculamos las varianzas y desviaciones t?picas
apply(multi_dist, 1, var)
apply(multi_dist, 1, sd)

# Calculamos las covarianzas
x = multi_dist[1,]
y = multi_dist[2,]

cov(x,y)


# Representamos el scatterplot

# Si no est? el paquete `scatterplot3d`, lo instalamos
if(!require("scatterplot3d")) { install.packages("scatterplot3d") }

# Cargamos el paquete `scatterplot3d`
require("scatterplot3d")

plot(x,y)

scatterplot3d(x, y, dmvnorm(t(multi_dist), mean = Mu, sigma = Sigma))
