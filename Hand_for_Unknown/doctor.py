from flask import *
from database import *
import uuid
doctor=Blueprint('doctor',__name__)
@doctor.route('/dochome',methods=['get','post'])
def dochome():
	return render_template('dochome.html')

@doctor.route('/docmanagetask',methods=['get','post'])
def docmanagetask():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="update":
		q="select * from tasks where task_id='%s'"%(id)
		res=select(q)
		data['updateprt']=res
	if 'update' in request.form:
		title=request.form['title']
		des=request.form['des']
		q="update tasks  set title='%s',description='%s' where task_id='%s'"%(title,des,id)
		update(q)
		return redirect(url_for('doctor.docmanagetask'))
	if action=="delete":
		q="delete from tasks where task_id='%s'"%(id)
		delete(q)
		return redirect(url_for('doctor.docmanagetask'))				
	if 'submit' in request.form:
		title=request.form['title']
		des=request.form['des']
		q="insert into tasks values(null,'%s','%s')"%(title,des)
		insert(q)
	q="select * from tasks"
	res=select(q)
	data['task']=res	
	return render_template('docmanagetask.html',data=data)	

@doctor.route('/docviewparents',methods=['get','post'])
def docviewparents():
	data={}
	q="select *,concat(fname,' ',lname)as NAME from parents"
	res=select(q)
	data['parent']=res
	return render_template('docviewparents.html',data=data)

@doctor.route('/docviewchild',methods=['get','post'])
def docviewchild():
	data={}
	id=request.args['id']
	q="select *,concat(fname,' ',lname)as NAME from child inner join parents using(parent_id) where parent_id='%s'"%(id)
	res=select(q)	
	data['childs']=res
	return render_template('docviewchild.html',data=data)

@doctor.route('/docviewmsg',methods=['get','post'])
def docviewmsg():
	data={}
	q="SELECT *,CONCAT(fname,' ',lname)AS NAME FROM `message` INNER JOIN parents USING(parent_id)"
	res=select(q)
	data['msg']=res
	j=0
	for i in range(1,len(res)+1):
		print('submit'+str(i))
		if 'submit' + str(i) in request.form:
			reply=request.form['reply'+str(i)]
			q="UPDATE message SET mess_reply='%s' WHERE message_id='%s'" %(reply,res[j]['message_id'])
			update(q)
			return redirect(url_for('doctor.docviewmsg')) 	
		j=j+1	
	return render_template('docviewmessage.html',data=data)	

@doctor.route('/docassigntask',methods=['get','post'])
def docassigntask():
	data={}
	ids=session['log_id']
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="update":
		q="select * from task_assign inner join child using(child_id) where assign_id='%s'"%(id)
		res=select(q)
		data['updateprt']=res
	if 'update' in request.form:
		adate=request.form['adate']
		dnotes=request.form['dnote']
		q="update task_assign set assign_date='%s',doctors_note='%s' where assign_id='%s'"%(adate,dnotes,id)
		update(q)
		return redirect(url_for('doctor.docassigntask'))
	if action=="delete":
		q="delete from task_assign where assign_id='%s'"%(id)
		delete(q)
		return redirect(url_for('doctor.docassigntask'))			
	if 'submit' in request.form:
		tasks=request.form['tasks']
		cname=request.form['cname']
		adate=request.form['adate']
		dnotes=request.form['dnote']
		q="insert into task_assign values(null,(select doctor_id from doctors where log_id='%s'),'%s','%s','%s','%s')"%(ids,cname,adate,dnotes,tasks)
		print(q)
		insert(q)
		flash("Assign Successfully")
	q="select * from child"
	res=select(q)
	data['childs']=res		
	q="select * from tasks"
	res=select(q)
	data['tasks']=res
	q="SELECT *,CONCAT(fname,' ',lname)as NAME FROM task_assign INNER JOIN doctors USING(doctor_id)INNER JOIN child USING(child_id) "
	res=select(q)
	data['ch']=res
	return render_template('docassigntask.html',data=data)

@doctor.route('/docanalyseperformance',methods=['get','post'])
def docanalyseperformance():
	data={}
	id=request.args['id']
	# if 'submit' in request.form:
	# 	per_des=request.form['pdes']
	# 	q="insert into task_performance values(null,'%s','%s',Curdate())"%(id,per_des)
	# 	insert(q)
	# 	return redirect(url_for('doctor.docassigntask'))
	q="select * from task_performance where assign_id='%s'" %(id)
	res=select(q)
	data['perfomance']=res
	return render_template('docanalyse_performance.html',data=data)
	
@doctor.route('/docaddgames',methods=['get','post'])
def docaddgames():
	data={}
	if 'submit' in request.form:
		title=request.form['title']
		des=request.form['des']
		q="insert into games values(null,'%s','%s')"%(title,des)
		insert(q)
	q="select * from games"
	res=select(q)
	data['game']=res	
	return render_template('doctoraddgames.html',data=data)		
@doctor.route('/docgamesdetails',methods=['get','post'])
def docgamesdetails():
	data={}
	id=request.args['id']
	if 'submit' in request.form:
		image=request.files['image']
		opt1=request.form['opt1']
		opt2=request.form['opt2']
		opt3=request.form['opt3']
		opt4=request.form['opt4']
		path='static/uploads/'+str(uuid.uuid4())+image.filename
		image.save(path)
		imagename=request.form['img']
		a=opt1+","+opt2+","+opt3+","+opt4
		q="insert into gameimage values(null,'%s','%s','%s','%s')"%(id,imagename,path,a)
		insert(q)
		return redirect(url_for('doctor.docaddgames'))
	q="select * from games where game_id='%s'"%(id)
	res=select(q)
	data['updateprt']=res	
	return render_template('docassigngame_details.html',data=data)
@doctor.route('/docassigngames',methods=['get','post'])
def docassigngames():
	data={}
	ids=session['log_id']
	if 'submit' in request.form:
		cname=request.form['cname']
		title=request.form['title']
		q="insert into assign_games values(null,(select doctor_id from doctors where log_id='%s'),'%s','%s',Curdate())"%(ids,cname,title)
		insert(q)
	q="select * from child"
	res=select(q)
	data['childs']=res
	q="select * from games"
	res=select(q)
	data['gm']=res
	return render_template('docassigngames.html',data=data)

@doctor.route('/docperformance',methods=['get','post'])
def docperformance():
	data={}
	q="SELECT *,CONCAT(fname,' ',lname)AS NAME FROM task_performance INNER JOIN task_assign USING(assign_id)INNER JOIN doctors USING(doctor_id)INNER JOIN child USING(child_id)"
	res=select(q)
	data['tasks']=res
	return render_template('docanalyseperformance.html',data=data)	




@doctor.route('/management_puzzle',methods=['get','post'])
def management_puzzle():
	import datetime

	if request.method=="POST":
		image=request.files['filefeild']
		date = datetime.datetime.now().strftime("%y%m%d-%H%M%S")
		image.save(r"D:\Ananthu anil\py+android\2023\viswajyothy\hfu\Hand_for_Unknown\static\pic\\" + date + '.jpg')
		p = "/static/pic/" + date + '.jpg'
		db=Db()
		db.insert("insert into puzzle values('','"+str(p)+"')")
		return '<script>alert("PUZZLE ADDED");window.location="view_puzzle"</script>'
	else:
		return render_template('doctor_manage_puzzle.html')


@doctor.route('/view_puzzle')
def view_puzzle():
    
	db=Db()
	qry=db.select("select * from puzzle")
	return render_template('doctor_view_puzzle.html',data=qry)
    

@doctor.route('/delete_puzzle/<pid>')
def delete_puzzle(pid):

	db=Db()
	db.delete("delete from puzzle where puzzle_id='"+pid+"'")
	return redirect('/doctor/view_puzzle')


@doctor.route('/docchat',methods=['post','get'])
def docchat():
    data={}
    uid=session['log_id']
    did=request.args['lid']
    if 'btn' in request.form:
        name=request.form['txt']
    
        q="insert into chat values(NULL,'%s','%s','%s',now())"%(uid,did,name)
        insert(q)
        return redirect(url_for("doctor.docchat",lid=did))
    q="SELECT * FROM chat WHERE (sender_id='%s' AND receiver_id='%s') OR (sender_id='%s' AND receiver_id=('%s')) order by message_id"%(uid,did,did,uid)
    # q="select * from chats where senderid='%s' and receiverid=( select login_id from doctors where doctor_id='%s' )"%(uid,did)
    print(q)
    res=select(q)
    data['ress']=res
    
    return render_template("doc_chat.html",data=data,uid=uid)



@doctor.route('/viewscores',methods=['GET','POST'])
def viewscores():
    data={}
    q="SELECT * FROM `game_result` INNER JOIN `assign_games` USING(`ass_game_id`) INNER JOIN games using (game_id) inner join child using(child_id) where  doctor_id=(select doctor_id from doctors where log_id='%s')"%(session['log_id'])
    res=select(q)
    data['game']=res
    
    for i in res:
        childid=i['child_id'] 
        q="select * from progress inner join  child using(child_id) inner join puzzle using(puzzle_id) where child_id='%s'"%(childid)
        res1=select(q)
        data['res1']=res1   
    return render_template('docviewgamescores.html', data=data)
