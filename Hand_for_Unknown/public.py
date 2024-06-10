from flask import *
from database import *
public=Blueprint('public',__name__)
@public.route('/',methods=['get','post'])
def publichome():
	return render_template('index.html')
@public.route('/gallery',methods=['get','post'])
def gallery():
	return render_template('gallery.html')
# @public.route('/contact',methods=['get','post'])
# def contact():
# 	return render_template('contact.html')	

@public.route('/login',methods=['get','post'])
def login():
	if 'submit' in request.form:
		username=request.form['username']
		password=request.form['password']
		q="select * from login where username='%s' and password='%s'"%(username,password)
		res=select(q)
		if res:
			session['log_id']=res[0]['log_id']
			if res[0]['usertype']=="admin":
				flash("Login Successfull")
				return redirect(url_for('admin.adminhome'))
			if res[0]['usertype']=="doctor":
				
				return redirect(url_for('doctor.dochome'))
				flash("Login Successfull")
		
	return render_template('login.html')	