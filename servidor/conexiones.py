import MySQLdb
def consultaSQL(consulta):
	respuesta=False
	cnx = MySQLdb.connect(host='localhost', user='root', passwd='',
		  db='micupongt')
	cursor = cnx.cursor()
	cursor.execute(consulta)
	respuesta=cursor.fetchone()
	if respuesta=='1':
		respuesta=True
		cnx.close()
	return respuesta
