import time
def generar_codigo(id_promocion,id_usuario):
	meses=['E','F','M','A','Y','J','L','G','S','C','N','B']
	#dia=time.strftime("%d")
	#mes=time.strftime("%m")
	dia=03
	mes=01
	substring=int(dia)+int(mes)
	codigo=str(meses[int(mes)-1])+str(id_usuario)+str(id_promocion)+str(substring)
	return codigo
codigo=generar_codigo(1,1)
print(codigo)
