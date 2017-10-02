import MySQLdb
def consultaSQL(consulta):
	respuesta=True
	cnx = MySQLdb.connect(host='localhost', user='root', passwd='',
		  db='micupongt')
	cursor = cnx.cursor()
	cursor.execute(consulta)
	cursor.fetchone()
	cnx.close()
	return respuesta
