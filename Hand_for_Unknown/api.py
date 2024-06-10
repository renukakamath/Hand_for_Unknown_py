from flask import Blueprint,request
from database import *
import demjson
import random
import cv2
from RandomForestClassifier import *

api=Blueprint('api',__name__)

systempath=r"D:/Ananthu anil/py+android/2023/viswajyothy/hfu/Hand_for_Unknown/static/pic/"
static_path=r"D:/Ananthu anil/py+android/2023/viswajyothy/hfu/Hand_for_Unknown/static/"

# import os

# base_dir = 'DD:\\Ananthu anil\\py+android\\2023\\viswajyothy\\hfu\\Hand_for_Unknown\\static'
# filename = 'pic\\230305-142426.jpg'
# filepath = os.path.join(base_dir, filename)



@api.route('/login/',methods=['get','post'])
def login():
	data={}
	username=request.args['username']
	password=request.args['password']
	q="select * from login where  username='%s' and password='%s'" %(username,password)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return  demjson.encode(data)

@api.route('/register/',methods=['get','post'])
def register():
	data={}
	
	first_name = request.args['firstname']
	last_name = request.args['lastname']
	house = request.args['house']
	place = request.args['place']
	pincode = request.args['pincode']
	phone = request.args['phone']
	email = request.args['email']
	username = request.args['username']
	password = request.args['password']
	
	q = "insert into login values(null,'%s','%s','parent')" % (username,password)
	login_id = insert(q)
	q = "insert into parents values(null,'%s','%s','%s','%s','%s','%s','%s','%s')" % (login_id,first_name,last_name,house,place,pincode,phone,email)
	print(q)
	insert(q)
	data['status'] = 'success'
	return demjson.encode(data)

@api.route('/childregister/',methods=['get','post'])
def childregister():
	data={}
	
	name = request.args['name']
	gender = request.args['gender']
	dob = request.args['dob']
	logid = request.args['logid']
	desc=request.args['desc']
	
	q = "insert into child values(null,(select parent_id from parents where log_id='%s'),'%s','%s','%s','%s')" % (logid,name,gender,dob,desc)
	login_id = insert(q)
	
	data['status'] = 'success'
	data['method']="childregister"
	return demjson.encode(data)

@api.route('/viewchilds/',methods=['get','post'])
def viewchilds():
	data={}
	logid=request.args['logid']
	
	q="select * from child where parent_id=(select parent_id from parents where log_id='%s')" %(logid)
	res=select(q)
	print(res)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="viewchilds"
	return  demjson.encode(data)



@api.route('/enquiry/',methods=['get','post'])
def enquiry():
	data={}
	
	enquirys = request.args['enquirys']
	logid = request.args['logid']
	
	q = "insert into enquiry values(null,(select parent_id from parents where log_id='%s'),'%s','pending',curdate())" % (logid,enquirys)
	login_id = insert(q)
	
	data['status'] ="success"
	data['method']="enquiry"
	return demjson.encode(data)

@api.route('/viewenquiry/',methods=['get','post'])
def viewenquiry():
	data={}
	logid=request.args['logid']
	
	q="select * from enquiry where parent_id=(select parent_id from parents where log_id='%s')" %(logid)
	res=select(q)
	print(res)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="viewenquiry"
	return  demjson.encode(data)

@api.route('/message/',methods=['get','post'])
def message():
	data={}
	
	messages = request.args['messages']
	logid = request.args['logid']
	
	q = "insert into message values(null,(select parent_id from parents where log_id='%s'),'%s','pending',curdate())" % (logid,messages)
	login_id = insert(q)
	
	data['status'] = 'success'
	data['method']="message"
	return demjson.encode(data)

@api.route('/viewmessage/',methods=['get','post'])
def viewmessage():
	data={}
	logid=request.args['logid']
	
	q="select * from message where parent_id=(select parent_id from parents where log_id='%s')" %(logid)
	res=select(q)
	print(res)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="viewmessage"
	return  demjson.encode(data)

@api.route('/viewdoctors/',methods=['get','post'])
def viewdoctors():
	data={}
	# logid=request.args['logid']
	
	q="select *,concat(fname,' ',lname) as name from doctors"
	res=select(q)
	print(res)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="viewdoctors"
	return  demjson.encode(data)


@api.route('/viewchildss/',methods=['get','post'])
def viewchildss():
	data={}
	logid=request.args['lid']
	
	q="select * from child where parent_id=(select parent_id from parents where log_id='%s')" %(logid)
	res=select(q)
	print(res)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="viewchildss"
	return  demjson.encode(data)


@api.route('/viewtask/',methods=['get','post'])
def viewtask():
	data={}
	logid=request.args['cid']
	
	q="select * from task_assign inner join doctors using(doctor_id) inner join tasks using(task_id)  where child_id='%s'" %(logid)
	res=select(q)
	print(res)

	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="viewtask"
	return  demjson.encode(data)

@api.route('/updatetaskperfomance/',methods=['get','post'])
def updatetaskperfomance():
	data={}
	
	assignid = request.args['assignid']
	perfomance = request.args['perfomance']
	q="select * from task_performance where assign_id='%s'" %(assignid)
	res=select(q)
	if res:
		q = "update task_performance set per_des='%s',per_date=curdate() where assign_id='%s'" %(perfomance,assignid)
		update(q)
	else:
		q = "insert into task_performance values(null,'%s','%s',curdate())" % (assignid,perfomance)
		insert(q)
	
	data['status'] = 'success'
	data['method']="updatetaskperfomance"
	return demjson.encode(data)


@api.route('/viewgame/',methods=['get','post'])
def viewgame():
	data={}
	logid=request.args['cid']
	
	q="select * from games inner join assign_games using(game_id) inner join doctors using(doctor_id) where child_id='%s'" %(logid)
	res=select(q)
	print(res)

	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="viewgame"
	return  demjson.encode(data)


@api.route('/selectimages/',methods=['get','post'])
def selectimages():
	data={}
	gid=request.args['gid']
	
	q="select * from gameimage where game_id='%s'" %(gid)
	res=select(q)
	print(res)

	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="selectimages"
	return  demjson.encode(data)


@api.route('/insertscore/',methods=['get','post'])
def insertscore():
	data={}
	
	assignid = request.args['assigndid']
	questions = request.args['questions']
	marks = request.args['marks']
	
	q = "insert into game_result values(null,'%s','%s','%s')" % (assignid,marks,questions)
	insert(q)
	
	data['status'] = 'success'
	data['method']="insertscore"
	return demjson.encode(data)


@api.route('/selectchild/',methods=['get','post'])
def selectchild():
    data={}
    cid=request.args['cid']
    q="select * from child where child_id=%s" % (cid)
    res=select(q)
    if res:
        data['status'] = 'success'
        data['data']=res
    else:
        data['status'] = 'failed'
    data['method']="viewchild"
    return str(data)



@api.route('updatechild/',methods=['get','post'])
def updatechild():
	data={}
	cid=request.args['cid']
	name=request.args['name']
	gender=request.args['gender']
	dob=request.args['dob']
	desc=request.args['desc']
	q="update child set name='%s',gender='%s',dob='%s',description='%s' where child_id=%s" % (name,gender,dob,desc,cid)
	res=update(q)
	if res:
		data['status'] ='success'
	else:
		data['status'] = 'failed'
	data['method']="update"
	return str(data)



@api.route("/and_view_puzzle",methods=['post','get'])
def and_view_puzzle():
	db=Db()
	data={}
	res=db.select("select * from puzzle")
	for i in res:
		i['fname']=i['image'].split("/")[-1]
		data['status']='ok'
		data['data']=res
	return str(data)




def cut_images(path, img_id):
	# image resizing

	fname=path.split("/")[-1]
	fpath=static_path + "pic/" +fname
	fname=fname.split(".")[0]
	from PIL import Image
	db = Db()
	img = Image.open(fpath)
	baseWidth = 800
	baseheight = 800
	img = img.resize((baseWidth, baseheight), Image.ANTIALIAS)
	img.save(static_path + 'jigsaw_resized\\' + img_id + ".jpg")
	iPath = "/static/jigsaw_resized/" + img_id + ".jpg"

	img = cv2.imread(static_path + "jigsaw_resized\\" + img_id + ".jpg")
	print("hhhhiiii")
	k = 1

	lst=[]
	for i in range(0, 800, 200):
		print("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii")
		for j in range(0, 800, 200):
			print("hrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr")
			crop_img = img[i:i + 200, j:j + 200]
			cv2.imshow('img', crop_img)
			cv2.imwrite(static_path + 'jigsaw_sliced\\' + str(k) + "-" + fname + ".jpg", crop_img)
			k = k + 1
	return  "ok"




@api.route('/AndJigsawRetrieveFile', methods=['POST'])
def AndJigsawRetrieveFile():
    db = Db()
    print("mmmsss")
    id = request.form["fid"]
    filepath = "/static/jigsaw_sliced/"
    ls=[]

    for i in range(1,17):
        fpath = filepath + str(i) + "-" + id+".jpg"
        ls.apiend(fpath)
    random.shuffle(ls)

    print(ls)
    print("kk22")

    return jsonify(status="ok",ls=ls)

# ------------



@api.route('/edt_fed_viw')
def ed_f_v():
    data={}
    pid=request.args['pid']
    db=Db()
    q="select * from puzzle where puzzle_id='%s'"%(pid)
    print(q)
    res=select(q)
    pth=res[0]['image']
    print(pth)
    cut_images(pth, pid)
    import random
    list = []
    for i in range(1000):
        r = random.randint(1, 16)
        if r not in list: list.append(r)
    abc = ""
    for ii in list:
        abc = abc + str(ii) + ","

    res = abc
    if res is not None:
        data['status'] = 'ok'
        data['data'] =res
    else:
        data['status']='no'
    return str(data)
	
	

    
    


@api.route('/UpdateProgress', methods=['post','get'])
def UpdateProgress():
    db =Db();
    data={}
    
    cid = request.args['cid']
    time = request.args['time']
    pid = request.args['pid']
    sol = 0
    agil = 0
    tot = 0
    time=time.replace(" ","")
    print(type)
    print(cid)
    print(time)
    timer =time.split(':')
    minute = int(timer[0])
    second = int(timer[1])
    minute = minute*60
    time = second+minute
    if time > 900:
        mark = 1
    elif time >600 and time<900:
        mark = 2
    elif time<600 and time>400:
        mark = 3
    elif time < 400 and time > 300:
        mark = 4
    elif  time < 300:
        mark = 5

    if time == 0:
        sol = 0
        agil= 0
    elif time == 1:
        sol = 1
        agil = 1
    elif time == 2:
        sol = 2
        agil = 2
    elif time == 3:
        sol = 3
        agil = 3
    elif time == 4:
        sol = 4
        agil = 4

    q="insert into progress values (null,'%s','%s',curdate(),'%s','%s')" % (pid,cid,time,mark)
    id=insert(q)
    if id>0:
        data['status']='ok'
    else:
        data['status']='failed'
    return str(data)





@api.route("/chatdetail")
def chatdetail():
	sid=request.args['sender_id']
	rid=request.args['receiver_id']
	
	data={}
	q="select * from chat where (sender_id='%s' and receiver_id='%s') or (sender_id='%s' and receiver_id='%s') group by message_id "%(sid,rid,rid,sid)
	
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		
		data['status']="failed"
	data['method']='chatdetail'
	
	return str(data)



@api.route("/chat")
def chat():
	data={}
	sid=request.args['sender_id']
	rid=request.args['receiver_id']
	det=request.args['details']

	q="insert into chat values(null,'%s','%s','%s',curdate())"%(sid,rid,det)
	insert(q)
	data['status']='success'
	data['method']='chat'
	return str(data)


@api.route('/result/', methods=['GET', 'POST'])
def result():
    data = {}
    aid=request.args['aid']
    cid=request.args['cid']
    
    
    q="SELECT * FROM `game_result` INNER JOIN `assign_games` USING(`ass_game_id`) INNER JOIN games using (game_id) where game_result.ass_game_id='%s' and assign_games.child_id='%s'"%(aid,cid)
    res=select(q)
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    return str(data)
	
	

@api.route('/checksymptoms/', methods=['GET', 'POST'])
def checksymptoms():
	data={}
	ss=[]
	sss=[]
	
	r1=request.args['r1']
	r2=request.args['r2']
	r3=request.args['r3']
	r4=request.args['r4']
	r5=request.args['r5']
	r6=request.args['r6']
	ss.append(r1)
	ss.append(r2)
	ss.append(r3)
	ss.append(r4)
	ss.append(r5)
	ss.append(r6)
	sss.append(ss)
	
	out=predicts(sss)
	print(out)
	if out[0]==0:
		data['out']='question and answer & Matching Color'
	if out[0]==1:
		data['out']='puzzle And Flappy bird'
	out=(data['out'])
	data['status']='success'
	data['out']=out
	return str(data)
 
 
@api.route('presult/')
def presult():
    data={}
   
    cid=request.args['cid']
    
    q="SELECT * FROM `progress` INNER JOIN child USING(child_id) INNER JOIN puzzle USING(puzzle_id) WHERE child_id='%s'"%(cid)
    res=select(q)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
        
    return str(data)

 
@api.route('viewparent/')
def viewparent():
	data={}
	lid=request.args['lid']
	q="SELECT *,concat(fname,lname) name FROM `parents` where not log_id='%s' "%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
		
	return str(data)


# @api.rout('recentchat')
# def recentchat():
#     logid=request.args['logid']