from flask import *
from database import *
admin=Blueprint('admin',__name__)
@admin.route('/adminhome',methods=['get','post'])
def adminhome():
	return render_template('adminhome.html')

@admin.route('/admanagedoctors',methods=['get','post'])
def admanagedoctors():	
	data={}
 
	from datetime import date
	today=date.today()
	data['today']=today
	
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
		# id1=request.args['id1']
	else:
		action=None	
	if action=="update":
		q="select * from doctors where doctor_id='%s'"%(id)
		res=select(q)
		data['updateprt']=res
	if 'update' in request.form:
		contact=request.form['contact']
		email=request.form['email']
		qual=request.form['qual']
		q="update doctors set phone='%s',email='%s',qualification='%s' where doctor_id='%s'"%(contact,email,qual,id)
		update(q)
		return redirect(url_for('admin.admanagedoctors'))
	if action=="delete":
		q="delete from doctors where doctor_id='%s'"%(id)
		delete(q)
		q="delete from login where log_id='%s'"%(id1)
		delete(q)
		return redirect(url_for('admin.admanagedoctors'))					
	if 'submit' in request.form:
		fname=request.form['fname']
		lname=request.form['lname']
		gender=request.form['gender']
		dob=request.form['dob']
		contact=request.form['contact']
		email=request.form['email']
		qual=request.form['qual']
		username=request.form['username']
		password=request.form['password']
		q="select username,password from login where username='%s' and password='%s'" %(username,password)
		print(q)
		result=select(q)
		if len(result)>0:
			flash("That username and password is already exist")
		else:	
			q="insert into login values(null,'%s','%s','doctor')"%(username,password)
			res=insert(q)
			q="insert into doctors values(null,'%s','%s','%s','%s','%s','%s','%s','%s')"%(res,fname,lname,gender,dob,contact,email,qual)
			insert(q)
			flash("Registration Successful")
	q="select *,concat(fname,' ',lname)as NAME from doctors"
	res=select(q)
	data['doctors']=res		
	return render_template('admanagedoctors.html',data=data)	

@admin.route('/adviewtask',methods=['get','post'])
def adviewtask():
	data={}
	q="select * from tasks"
	res=select(q)
	data['task']=res
	return render_template('adviewtask.html',data=data)	

@admin.route('/adviewparents',methods=['get','post'])
def adviewparents():
	data={}
	q="SELECT *,CONCAT(fname,' ',lname)AS NAME FROM parents "
	res=select(q)
	data['parent']=res
	return render_template('adviewparents.html',data=data)

@admin.route('/adviewchild',methods=['get','post'])
def adviewchild():
	data={}
	id=request.args['id']
	q="select *,concat(fname,' ',lname)as NAME from child inner join parents using(parent_id) where parent_id='%s'"%(id)
	res=select(q)
	data['childs']=res
	return render_template('adviewchild.html',data=data)
@admin.route('/adviewenq',methods=['get','post'])
def adviewenq():
	data={}
	q="SELECT *,CONCAT(fname,' ',lname)AS NAME FROM `enquiry` INNER JOIN parents USING(parent_id)"
	res=select(q)
	data['enq']=res
	j=0
	for i in range(1,len(res)+1):
		print('submit'+str(i))
		if 'submit' + str(i) in request.form:
			reply=request.form['reply'+str(i)]
			print(reply)
			print(j)
			print(res[j]['enquiry_id'])
			q="UPDATE enquiry SET reply='%s' WHERE enquiry_id='%s'" %(reply,res[j]['enquiry_id'])
			print(q)
			update(q)
			return redirect(url_for('admin.adviewenq')) 	
		j=j+1	
	return render_template('adviewenquiry.html',data=data)


