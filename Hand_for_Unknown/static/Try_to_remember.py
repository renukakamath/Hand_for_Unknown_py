import random
import datetime

import matplotlib
from flask import Flask,render_template,request,redirect, session,jsonify
from DBConnection import Db
import cv2
import numpy as np
import imutils
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, Flatten
from tensorflow.keras.layers import Conv2D
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.layers import MaxPooling2D
from tensorflow.keras.preprocessing.image import ImageDataGenerator



app = Flask(__name__)
app.secret_key="hiiii"
systempath=r"D:\Ananthu anil\py+android\2023\viswajyothy\try to remember\Try_to_remember web\static\pic\\"
static_path=r"D:\Ananthu anil\py+android\2023\viswajyothy\try to remember\Try_to_remember web\static\\"

@app.route('/')
def a():
    return render_template("index.html")

@app.route('/login',methods=['get','post'])
def login():
    if request.method=="POST":
        username=request.form['textfield']
        password=request.form['textfield2']
        db=Db()
        a=db.selectOne("select * from login where user_name='"+username+"' and password='"+password+"'")
        if a is not None:
            session['lg'] = "lin"

            if a['user_type']=="admin":
                session['lg']="lin"
                return redirect('/view_admin')
            if a['user_type']=="doctor":
                session['lid']=a['login_id']
                session['lg']="lin"

                return redirect("/dd")
            if a['user_type']=="caretaker":
                session['lid']=a['login_id']


                return redirect("/cc")
            else:
                return '<script>alert("Invalid user");window.location="/"</script>'
        else:
            return '<script>alert("Invalid user");window.location="/"</script>'

    else:
        return render_template("login.html")

@app.route('/logout')
def logout():
    session.clear()
    session['lg']=""
    return redirect('/')


@app.route('/view_admin')
def view_admin():

        return render_template('adminindex.html')

@app.route('/add_doctor',methods=['get','post'])
def add_doctor():
    if session['lg']=="lin":
        if request.method=="POST":
            doctorname=request.form['textfield']
            specialization=request.form['textfield12']
            phoneno=request.form['textfield2']
            email=request.form['textfield3']
            image=request.files['fileField']
            qualification=request.form['select']
            experience=request.form['textarea']
            housename=request.form['textfield4']
            place=request.form['textfield5']
            post=request.form['textfield6']
            pin=request.form['textfield7']
            pswd=random.randint(0000,9999)
            date=datetime.datetime.now().strftime("%y%m%d-%H%M%S")
            image.save(r"C:\Users\DELL\PycharmProjects\Try_to_remember\static\pic\\"+date+'.jpg')
            p="/static/pic/"+date+'.jpg'
            db=Db()
            q=db.insert("INSERT INTO login(login_id,user_name,password,user_type) VALUES ('','"+email+"','"+str(pswd)+"','doctor')")
            db.insert("insert into doctor(doctor_id,doctor_name,specialisation,phone_no,email,image,qualification,experiance,house_name,place,post,pin) values('"+str(q)+"','"+doctorname+"','"+specialization+"','"+phoneno+"','"+email+"','"+str(p)+"','"+qualification+"','"+experience+"','"+housename+"','"+place+"','"+post+"','"+pin+"')")
            return'<script>alert("ADD SUCCESSFULLY");window.location="/view_admin"</script>'
        else:
            return render_template("admin/doctor_add.html")
    else:
        return redirect('/')


@app.route('/update_doctor/<did>',methods=['get','post'])
def update_doctor(did):
    if session['lg'] == "lin":
        if request.method == "POST":
            doctorname = request.form['textfield']
            specialization = request.form['textfield12']
            phoneno = request.form['textfield2']
            email = request.form['textfield3']
            qualification = request.form['select']
            experience = request.form['textarea']
            housename = request.form['textfield4']
            place = request.form['textfield5']
            post = request.form['textfield6']
            pin = request.form['textfield7']
            db = Db()
            db.update("update doctor set doctor_name='" + doctorname + "', specialisation='" + specialization + "',phone_no='" + phoneno + "',email='" + email + "',qualification='" + qualification + "',experiance='" + experience + "',house_name='" + housename + "',place='" + place + "',post='" + post + "',pin='" + pin + "'where doctor_id='" + did + "'")
            if 'fileField' in request.files:
                image = request.files['fileField']
                if image.filename != "":
                    date = datetime.datetime.now().strftime("%y%m%d-%H%M%S")
                    image.save(r"C:\Users\DELL\PycharmProjects\Try_to_remember\static\pic\\" + date + '.jpg')
                    p = "/static/pic/" + date + '.jpg'
                else:
                    pass
            else:
                pass

            return '<script>alert("UPDATE SUCCESSFULLY");window.location="/view_admin"</script>'
        else:
            db = Db()
            qry = db.selectOne("select * from doctor where doctor_id='" + did + "'")
            return render_template('admin/doctor_update.html', data=qry)
    else:
        return redirect('/')

@app.route('/view_doctor')
def view_doctor():
    if session['lg'] == "lin":
        db=Db()
        qry=db.select("select * from doctor")
        return render_template('admin/doctormanagement.html',data=qry)
    else:
        return redirect('/')

@app.route('/delete_doctor/<did>')
def delete_doctor(did):
    if session['lg'] == "lin":
        db=Db()
        db.delete("delete from doctor where doctor_id='"+did+"'")
        return '<script>alert("DELETE SUCCESSFULLY");window.location="/view_doctor"</script>'
    else:
        return redirect('/')

@app.route('/view_patient')
def view_patient():
    if session['lg'] == "lin":
        db=Db()
        qry=db.select("select * from patient")
        return render_template('admin/patient_view.html',data=qry)
    else:
        return redirect('/')

@app.route('/management_puzzle',methods=['get','post'])
def management_puzzle():
    if session['lg'] == "lin":
        if request.method=="POST":
            image=request.files['fileField']
            date = datetime.datetime.now().strftime("%y%m%d-%H%M%S")
            image.save(r"C:\Users\DELL\PycharmProjects\Try_to_remember\static\pic\\" + date + '.jpg')
            p = "/static/pic/" + date + '.jpg'
            db=Db()
            db.insert("insert into puzzle values('','"+str(p)+"')")
            return '<script>alert("PUZZLE ADDED");window.location="/view_admin"</script>'
        else:
            return render_template('admin/puzzle_management.html')
    else:
        return redirect('/')

@app.route('/view_puzzle')
def view_puzzle():
    if session['lg']=="lin":
        db=Db()
        qry=db.select("select * from puzzle")
        return render_template('admin/puzzle_view.html',data=qry)
    else:
        return redirect('/')

@app.route('/delete_puzzle/<pid>')
def delete_puzzle(pid):
    if session['lg'] == "lin":
        db=Db()
        db.delete("delete from puzzle where puzzle_id='"+pid+"'")
        return redirect('/view_puzzle')
    else:
        return redirect('/')

###############################################       DOCTOR             ########################################################
###############################################       DOCTOR             ########################################################
@app.route("/doc_home")
def doc_home():
    return render_template("doctor/home.html")

@app.route('/dd')
def c():
    return render_template("doctor/doctorindex.html")

@app.route('/view_profile')
def view_profile():
    if session['lg'] == "lin":
        db=Db()
        qry=db.selectOne("select*from doctor where doctor_id='"+str(session['lid'])+"'")
        print(qry)
        return render_template('doctor/view_profile.html',data=qry)
    else:
        return redirect('/')

@app.route('/patient_add',methods=['get','post'])
def patient_add():
    if session['lg'] == "lin":
        if request.method == "POST":
          patientname=request.form['textfield']
          age=request.form['textfield2']
          gender=request.form['radiog']
          dateofbirth=request.form['textfield3']
          phoneno=request.form['textfield4']
          email=request.form['textfield5']
          p = random.randint(0000, 9999)
          db=Db()
          qry = db.insert("INSERT INTO login(login_id,user_name,password,user_type) VALUES (NULL,'" + email + "','" + str(p) + "','patient')")
          db.insert("insert into patient values('"+str(qry)+"','"+patientname+"','"+age+"','"+gender+"','"+dateofbirth+"','"+phoneno+"','"+email+"','"+str(session['lid'])+"')")
          return'<script>alert("ADD SUCCESSFULLY");window.location="/patient_add"</script>'
        else:
            return render_template("doctor/add_patient.html")
    else:
        return redirect('/')

@app.route('/view_patient2')
def view_patient2():
    if session['lg'] == "lin":
        db=Db()
        qry=db.select("select * from patient where patient.doctor_id='"+str(session['lid'])+"'")
        print(qry)
        return render_template('doctor/patient_view.html', data=qry)
    else:
        return redirect('/')

@app.route('/UPDATE_patient/<did>',methods=['get','post'])
def update(did):
    if session['lg'] == "lin":
        if request.method == "POST":
            patientname = request.form['textfield']
            age = request.form['textfield2']
            gender = request.form['RadioGroup1']
            dateofbirth = request.form['textfield3']
            phoneno = request.form['textfield4']
            email = request.form['textfield5']
            db = Db()
            db.update("update patient set patient_name='" + patientname + "', age='" + age + "',gender='" + gender + "',date_of_birth='" + dateofbirth+ "',phone_no='" + phoneno + "',email='" + email+"' where patient_id='"+did+"'")
            return '<script>alert("UPDATE SUCCESSFULLY");window.location="/view_patient2"</script>'
        else:
            db=Db()
            qry=db.selectOne("select * from patient  where patient_id='"+did+"'")
            return render_template("doctor/UPDATE.html",data=qry)
    else:
        return redirect('/')

@app.route('/delete_patient/<did>')
def delete_patient(did):
    if session['lg'] == "lin":
        db = Db()
        db.delete("delete from patient where patient_id='"+did+"'")
        return redirect('/view_patient2')
    else:
        return redirect('/')


@app.route('/add_ct',methods=['get','post'])
def add_ct():
    if session['lg'] == "lin":
        if request.method == "POST":
            caretakername=request.form['textfield']
            experiance=request.form['textareaa']
            qualification = request.form['select1']
            phoneno=request.form['textfield12']
            email=request.form['textfield13']
            housename=request.form['textfield4']
            place=request.form['textfield5']
            post=request.form['textfield6']
            pin=request.form['textfield7']
            p = random.randint(0000, 9999)
            db=Db()
            qry=db.insert("INSERT INTO login(login_id,user_name,password,user_type) VALUES (NULL,'" + email + "','" + str(p) + "','caretaker')")
            db.insert("insert into  caretaker values('"+str(qry)+"','"+caretakername+"','"+experiance+"','"+qualification+"','"+phoneno+"','"+email+"','"+housename+"','"+place+"','"+post+"','"+pin+"','"+str(session['lid'])+"')")
            return '<script>alert("ADD SUCCESSFULLY");window.location="/add_ct"</script>'
        else:
               return render_template("doctor/add_ct.html")
    else:
        return redirect('/')

@app.route('/view_ct')
def view_ct():
    if session['lg'] == "lin":
        db=Db()
        qry=db.select("select * from caretaker where caretaker.doctor_id='"+str(session['lid'])+"'")
        print(qry)
        return render_template('doctor/ct_view.html',data=qry)
    else:
        return redirect('/')

@app.route('/update_ct/<lid>',methods=['get','post'])
def update_ct(lid):
    if session['lg'] == "lin":
        if request.method == "POST":
            caretakername = request.form['textfield']
            experiance = request.form['textareaa']
            qualification = request.form['select1']
            phoneno = request.form['textfield12']
            email = request.form['textfield13']
            housename = request.form['textfield4']
            place = request.form['textfield5']
            post = request.form['textfield6']
            pin = request.form['textfield7']
            db = Db()
            db.update("update caretaker set caretaker_name= '"+caretakername+"',experiance='"+experiance+"',qualification='"+qualification+"',phone_no='"+phoneno+"',email='"+email+"',house_name='"+housename+"',place='"+place+"',post='"+post+"',pin='"+pin+"'where caretaker_id='"+lid+"'")
            return '<script>alert("UPDATE SUCCESSFULLY");window.location="/view_ct"</script>'
        else:
            db=Db()
            res=db.selectOne("select * from caretaker where caretaker_id='"+lid+"'")
            return render_template("doctor/update_ct.html",data=res)
    else:
        return redirect('/')

@app.route('/delete_ct/<lid>')
def delete_ct(lid):
    if session['lg'] == "lin":
        db=Db()
        db.delete("delete from caretaker where caretaker_id='"+lid+"'")
        return redirect('/view_ct')
    else:
        return redirect('/')


@app.route('/add_notification',methods=['get','post'])
def add_notification():
    if session['lg'] == "lin":
        if request.method == "POST":
            n=request.form['textarea']
            db=Db()
            qry=db.insert("INSERT INTO notification(doctor_id,`date`,notification) VALUES ('"+str(session['lid'])+"',curdate(),'"+n+"')")
            return '<script>alert("SEND SUCCESSFULLY");window.location="/add_notification"</script>'
        else:
               return render_template("doctor/add_notify.html")
    else:
        return redirect('/')

@app.route('/view_notify')
def view_notify():
    if session['lg'] == "lin":
        db=Db()
        qry=db.select("select *from doctor,notification where notification.doctor_id=doctor.doctor_id and notification.doctor_id='"+str(session['lid'])+"'")
        print(qry)
        return render_template('doctor/view_notification.html', data=qry)
    else:
        return redirect('/')

@app.route('/delete_notify/<nid>')
def delete_notify(nid):
    if session['lg'] == "lin":
        db=Db()
        db.delete("delete from notification where notification_id='"+nid+"'")
        return '<script>alert("DELETE SUCCESSFULLY");window.location="/view_notify"</script>'
    else:
        return redirect('/')


@app.route('/allocate_caretaker/<pid>',methods=['get','post'])
def allocate_caretaker(pid):
    if session['lg'] == "lin":
        if request.method == "POST":
            a=request.form['select2']
            db = Db()
            db.insert("INSERT INTO allocation VALUES ( '','"+str(pid)+"','"+str(a)+"')")
            return'<script>alert("ALLOCATE SUCCESSFULLY");window.location="/view_patient2"</script>'
        else:
            db=Db()
            res=db.select("select * from caretaker")
            return render_template("doctor/allocate_caretaker.html",data=res)
    else:
        return redirect('/')

@app.route('/view_allocation')
def view_allocation():
    if session['lg'] == "lin":
        db=Db()
        qry=db.select("select * from allocation,caretaker,patient where allocation.patient_id=patient.patient_id and caretaker.caretaker_id=allocation.caretaker_id")
        print(qry)
        return render_template("doctor/view_allocation.html",data=qry)
    else:
        return redirect('/')

@app.route('/delete_allocation/<pid>')
def delete_allocation(pid):
    if session['lg'] == "lin":
        db=Db()
        db.delete("delete from allocation where allocation_id='"+pid+"'")
        return '<script>alert("DELETE SUCCESSFULLY");window.location="/view_allocation"</script>'
    else:
        return redirect('/')

@app.route('/dr_progress/<pid>', methods=['get', 'post'])
def dr_progress(pid):
        if session['lg'] == "lin":
            if request.method=="POST":
                type=request.form['select']
                db = Db()
                qry = db.select("select * from result where patient_id='" + pid + "' and type='"+type+"'")
                print(qry)
                x = []
                y = []
                for i in qry:
                    x.append(i['date'])
                    y.append(int(i['correct']))
                print(x)
                print(y)
                path = plot_progress(x, y)
                if len(qry) > 0:
                    return render_template('doctor/dr_progress.html', path=path, data=qry)
                else:
                    return render_template('doctor/dr_progress.html', error="No data")
            else:
                return render_template('doctor/dr_progress.html')
        else:
            return redirect("/")


@app.route('/view_emotion')
def view_emotion():
    if session['lg'] == "lin":
        db=Db()
        qry=db.select("select * from emotion ,patient where emotion.patient_id=patient.patient_id and patient.doctor_id='"+str(session['lid'])+"'")
        print(qry)
        return render_template("doctor/view_emotion.html",data=qry)
    else:
        return redirect('/')
##############################################################  caretaker  #############################################################
#############################################################   caretaker  #############################################################
@app.route("/caretaker_home")
def caretaker_home():
    return render_template("caretaker/homec.html")

@app.route('/cc')
def d():
    return render_template("caretaker/ctindex.html")



@app.route('/view_apatient')
def view_apatient():
    if session['lg'] == "lin":
        db=Db()
        qry=db.select("select * from patient,allocation where patient.patient_id=allocation.patient_id and allocation.caretaker_id='"+str(session['lid'])+"'")
        return render_template("caretaker/view_apatient.html",data=qry)
    else:
        return redirect('/')

@app.route('/add_date/<cid>',methods=['get','post'])
def add_date(cid):
    if session['lg'] == "lin":
      if request.method == "POST":
         date= request.form['textfield']
         title=request.form['textfield2']
         description=request.form['textarea']
         db = Db()
         db.insert("INSERT INTO date VALUES ('','"+cid+"','"+date+"','"+title+"','"+description+"')")
         return '<script>alert("ADD SUCCESSFULLY");window.location="/view_apatient"</script>'
      else:
           return render_template("caretaker/add_date.html")
    else:
        return redirect('/')

@app.route('/add_person/<cid>',methods=['get','post'])
def add_person(cid):
    if session['lg'] == "lin":
        if request.method=="POST":
             personname=request.form['textfield']
             image=request.files['fileField']
             phonenumber=request.form['textfield2']
             housename=request.form['textfield3']
             place=request.form['textfield4']
             # post=request.form['textfield5']
             pin=request.form['textfield6']
             date = datetime.datetime.now().strftime("%y%m%d-%H%M%S")
             image.save(r"C:\Users\DELL\PycharmProjects\Try_to_remember\static\pic\\" + date + '.jpg')
             c= "/static/pic/" + date + '.jpg'
             db=Db()
             db.insert("INSERT INTO person VALUES('','"+personname+"','"+str(c)+"','"+phonenumber+"','"+housename+"','"+place+"','"+pin+"','"+cid+"')")
             return '<script>alert("ADD SUCCESSFULLY");window.location="/view_apatient"</script>'
        else:
             return render_template("caretaker/add_person.html")
    else:
        return redirect('/')

@app.route('/view_notification')
def view_notification():
    if session['lg'] == "lin":
        db = Db()
        qry = db.select("select * from notification,doctor where doctor.doctor_id=notification.doctor_id")
        print(qry)
        return render_template('caretaker/view_notification.html', data=qry)
    else:
        return redirect('/')

@app.route('/view_progress/<pid>', methods=['get','post'])
def view_progress(pid):
    if session['lg'] == "lin":
        if request.method == "POST":
            type = request.form['select']
            db = Db()
            qry = db.select("select * from result where patient_id='" + pid + "' and type='" + type + "'")
            print(qry)
            x = []
            y = []
            for i in qry:
                x.append(i['date'])
                y.append(int(i['correct']))
            print(x)
            print(y)
            path = plot_progress(x, y)
            if len(qry) > 0:
                return render_template('caretaker/view_progress.html', path=path, data=qry)
            else:
                return render_template('caretaker/view_progress.html', error="No data")
        else:
            return render_template('caretaker/view_progress.html')
    else:
        return redirect('/')

 # importing the required module
import matplotlib.pyplot as plt
matplotlib.use('Agg')
def plot_progress(x, y):


    # plotting the points
    plt.plot(x, y)

    # naming the x axis
    plt.xlabel('Date')
    # naming the y axis
    plt.ylabel('Correct answer')
    import time
    dt=time.strftime("%Y%m%d_%H%M%S")
    plt.savefig(static_path + "graph\\" +dt + ".png")
    path="/static/graph/"+dt + ".png"
    return path


@app.route('/delete_notification/<nid>')
def delete_notification(nid):
    if session['lg'] == "lin":
        db=Db()
        db.delete("delete from notification where notification_id='"+nid+"'")
        return '<script>alert("DELETE SUCCESSFULLY");window.location="/view_notification"</script>'

    else:
        return redirect('/')


@app.route('/about')
def about():
    return render_template("about.html")
##################################################### android ###################################################
@app.route('/and_login',methods=['post'])
def and_login():
    username=request.form['u']
    password=request.form['p']
    db=Db()
    res=db.selectOne("select * from login where user_name='"+username+"' and password='"+password+"'")
    if res is not None:
        return  jsonify(status="ok",lid=res['login_id'],type=res['user_type'])
    else:
        return jsonify(status="none")

@app.route('/and_view_date',methods=['post'])
def and_view_date():
    lid=request.form['id']
    db=Db()
    res=db.select("select * from date ,patient where patient.patient_id=date.patient_id and date.patient_id='"+lid+"'")
    print(res)
    if len(res)>0:
        return jsonify(status="ok",data=res)
    else:
        return jsonify(status="none")

@app.route('/and_view_person',methods=['post'])
def and_view_person():
    lid=request.form['id']
    db=Db()
    res=db.select("select * from person,patient where person.patient_id=patient.patient_id and person.patient_id='"+lid+"'")
    print(res)
    if len(res)>0:
        return jsonify(status="ok",data=res)
    else:
        return jsonify(status="none")

@app.route('/and_view_emotion',methods=['post'])
def and_view_emotion():
    lid = request.form['id']
    db=Db()
    res=db.select("select * from emotion,patient where emotion.patient_id=patient.patient_id and emotion.patient_id='"+lid+"'")
    print(res)
    if len(res)>0:
        return jsonify(status="ok",data=res)
    else:
        return jsonify(status="none")

@app.route('/and_view_reminder',methods=['post'])
def and_view_reminder():
    lid=request.form['id']
    db=Db()
    res=db.select("select * from reminder,patient where reminder.patient_id=patient.patient_id and reminder.patient_id='"+lid+"' ")
    print(res)
    if len(res)>0:
        return jsonify(status="ok",data=res)
    else:
        return jsonify(status="none")

    ############################### speech #################################

@app.route("/and_view_persons", methods=['post'])
def and_view_persons():
    lid=request.form['lid']
    db=Db()
    res=db.select("select * from person,patient where person.patient_id=patient.patient_id and person.patient_id='"+lid+"'")
    if len(res) > 0:
      return jsonify(status="ok", data=res)
    else:
        return jsonify(status="no")

@app.route("/and_check_ans", methods=['post'])
def and_check_ans():
    lid=request.form['lid']
    ans=request.form['ans']
    ans=ans.lower()
    pos=request.form['pos']
    print("pos ", pos)
    db=Db()
    res=db.select("select * from person,patient where person.patient_id=patient.patient_id and person.patient_id='"+lid+"'")
    correct_ans=res[int(pos)]["person_name"]
    print("Correct  ", correct_ans)
    print("Answered  ", ans)
    lst=correct_ans.split(" ")
    if ans in lst:
        print("1")
        return jsonify(status="ok")
    else:
        for i in lst:
            i=i.lower()
            print(i)
            if i in ans:
                print("2")
                return jsonify(status="ok")
        print("3")
        return jsonify(status="no")



@app.route("/and_insert_speech_marks", methods=['post'])
def and_insert_speech_marks():
    lid=request.form['lid']
    correct=request.form['correct']
    attended=request.form['attended']
    db=Db()
    res=db.selectOne("select * from `result` WHERE patient_id='"+lid+"' AND DATE=CURDATE()")
    if res is None:
        db.insert("INSERT INTO `result`( patient_id,date,type, attend, correct) VALUES( '"+lid+"',curdate(),'speech','"+attended+"' ,'"+correct+"')")
    else:
        db.update("update result set attend='"+attended+"', correct='"+correct+"' where result_id='"+str(res['result_id'])+"'")
    return jsonify(status="ok")

############################################## emotion #############################################

@app.route('/insertmood',methods=['get','post'])
def insertmood():
        lid=request.form['lid']
        p=request.files['pic']
        obj=Db()
        p.save(systempath+"cap.jpg")
        model = Sequential()

        model.add(Conv2D(32, kernel_size=(3, 3),
                         activation='relu',
                         input_shape=(48, 48, 1)))
        model.add(Conv2D(64, kernel_size=(3, 3),
                         activation='relu'))
        model.add(MaxPooling2D(pool_size=(2, 2)))
        model.add(Dropout(0.25))

        model.add(Conv2D(128, kernel_size=(3, 3),
                         activation='relu'))
        model.add(MaxPooling2D(pool_size=(2, 2)))
        model.add(Conv2D(128, kernel_size=(3, 3),
                         activation='relu'))
        model.add(MaxPooling2D(pool_size=(2, 2)))
        model.add(Dropout(0.25))

        model.add(Flatten())
        model.add(Dense(1024, activation='relu'))
        model.add(Dropout(0.5))
        model.add(Dense(7, activation='softmax'))

        model.load_weights(r'C:\Users\DELL\PycharmProjects\Try_to_remember\model.h5')

        # prevents openCL usage and unnecessary logging messages
        cv2.ocl.setUseOpenCL(False)

        # dictionary which assigns each label an emotion (alphabetical order)
        emotion_dict = {0: "Angry",
                        1: "Disgusted",
                        2: "Fearful", 3: "Happy",
                        4: "Neutral", 5: "Sad",
                        6: "Surprised"}

        frame = cv2.imread(systempath + "cap.jpg")

        facecasc = cv2.CascadeClassifier(
            r'C:\Users\DELL\PycharmProjects\Try_to_remember\static\haarcascade_frontalface_default (1).xml')
        gray = cv2.cvtColor(frame,
                            cv2.COLOR_BGR2GRAY)
        faces = facecasc.detectMultiScale(gray,
                                          scaleFactor=1.3,
                                          minNeighbors=5)

        for (x, y, w, h) in faces:
            cv2.rectangle(frame, (x, y - 50),
                          (x + w, y + h + 10),
                          (255, 0, 0), 2)
            roi_gray = gray[y:y + h, x:x + w]
            cropped_img = np.expand_dims(
                np.expand_dims(
                    cv2.resize(roi_gray,
                               (48, 48)), -1), 0)
            prediction = model.predict(
                cropped_img)
            print(prediction)
            maxindex = int(np.argmax(prediction))
            print(emotion_dict[maxindex])
        result=obj.insert("insert into `emotion`(`patient_id`,`emotion`,`date`,`time`) values ( '"+lid+"','"+str(emotion_dict[maxindex])+"',curdate(),curtime());")
        if result:
           return jsonify(status="ok",name=emotion_dict[maxindex])
        else:
            return jsonify(status="none")

@app.route("/hand_result", methods=['post'])
def hand_result():
    sid=request.form['sid']
    att=request.form['att']
    cor=request.form['cor']
    db=Db()
    res=db.selectOne("select * from speech_test_result where patient_id='"+sid+"' and date=curdate()")
    if res is None:
        db.insert("insert into speech_test_result(patient_id, date, attended, correct) values('"+sid+"', curdate(), '"+att+"','"+cor+"')")
    else:
        db.update("update speech_test_result set attended='"+att+"', correct='"+cor+"' where result_id='"+str(res['result_id'])+"'")
    return jsonify(status="ok")


############################writing###################################################################################


@app.route("/handwriting", methods=['post'])
def handwriting():
    image = request.form['image']
    import base64
    a = base64.b64decode(image)
    fh = open(static_path+"test.bmp", "wb")
    fh.write(a)
    fh.close()
    from scan import predict
    try:
        s1 = predict()
        print("output - ", s1)
        res1 = {}
        if s1:
            if s1 == 3349:
                s = u'ക'
            elif s1 == 3333:
                s = u'അ'
            elif s1 == 3334:
                s = u'ആ'
            elif s1 == 3335:
                s = u'ഇ'
            elif s1 == 3337:
                s = u'ഉ'
            elif s1 == 3342:
                s = u'എ'
            elif s1 == 3343:
                s = u'ഏ'
            elif s1 == 3346:
                s = u'ഒ'
            elif s1 == 3349:
                s = u'ക'
            elif s1 == 3350:
                s = u'ഖ'
            elif s1 == 3351:
                s = u'ഗ'
            elif s1 == 3352:
                s = u'ഘ'
            elif s1 == 3353:
                s = u'ങ'
            elif s1 == 3354:
                s = u'ച'
            elif s1 == 3355:
                s = u'ഛ'
            elif s1 == 3356:
                s = u'ജ'
            elif s1 == 3357:
                s = u'ഝ'
            elif s1 == 3358:
                s = u'ഞ'
            elif s1 == 3359:
                s = u'ട'
            elif s1 == 3360:
                s = u'ഠ'
            elif s1 == 3361:
                s = u'ഡ'
            elif s1 == 3362:
                s = u'ഢ'
            elif s1 == 3363:
                s = u'ണ'
            elif s1 == 3364:
                s = u'ത'
            elif s1 == 3365:
                s = u'ഥ'
            elif s1 == 3366:
                s = u'ദ'
            elif s1 == 3367:
                s = u'ധ'
            elif s1 == 3368:
                s = u'ന'
            elif s1 == 3370:
                s = u'പ'
            elif s1 == 3371:
                s = u'ഫ'
            elif s1 == 3372:
                s = u'ബ'
            elif s1 == 3373:
                s = u'ഭ'
            elif s1 == 3374:
                s = u'മ'
            elif s1 == 3375:
                s = u'യ'
            elif s1 == 3376:
                s = u'ര'
            elif s1 == 3377:
                s = u'റ'
            elif s1 == 3378:
                s = u'ല'
            elif s1 == 3379:
                s = u'ള'
            elif s1 == 3380:
                s = u'ഴ'
            elif s1 == 3381:
                s = u'വ'
            elif s1 == 3382:
                s = u'ശ'
            elif s1 == 3383:
                s = u'ഷ'
            elif s1 == 3384:
                s = u'സ'
            elif s1 == 3385:
                s = u'ഹ'
            else:
                s = '[]'
        if s:
            return jsonify(status='ok', output=s, code=s1)
        else:
            return jsonify(status='none')
    except Exception as e:
        print(e)
        return jsonify(status='none')




@app.route("/hand_result1", methods=['post'])
def hand_result1():
    sid=request.form['sid']
    stat=request.form['mark']
    db=Db()
    q=db.selectOne("select * from result where patient_id='"+sid+"' and date=curdate() and type='writing'")
    if q is None:
        db.insert("insert into result(patient_id,date,type,correct) values('"+sid+"',curdate(),'writing','"+stat+"')")
    else:
        db.update("update result set correct=correct+'"+stat+"' where result_id='"+str(q['result_id'])+"'")
    return jsonify(status='ok')


@app.route("/and_view_puzzle",methods=['post'])
def and_view_puzzle():
    db=Db()
    res=db.select("select * from puzzle")
    for i in res:
        i['fname']=i['image'].split("/")[-1]
    return jsonify(status='ok',data=res)



@app.route("/getallnots",methods=['post'])
def getallnots():
    db=Db()
    t=datetime.datetime.now().strftime("%H:%M")
    res=db.select("select * from reminder where patient_id='"+request.form['uid']+"' and date=curdate() and time='"+t+"'")
    if len(res)>0:
        return jsonify(status='ok',data=res)
    else:
        return jsonify(status='')




def cut_images(path, img_id):
    # image resizing

    fname=path.split("/")[-1]
    fpath=static_path + "pic\\" +fname
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
        for j in range(0, 800, 200):
            crop_img = img[i:i + 200, j:j + 200]
            cv2.imshow('img', crop_img)
            cv2.imwrite(static_path + 'jigsaw_sliced\\' + str(k) + "-" + fname + ".jpg", crop_img)
            k = k + 1
    return  "ok"


@app.route('/AndJigsawRetrieveFile', methods=['POST'])
def AndJigsawRetrieveFile():
    db = Db()
    print("mmmsss")
    id = request.form["fid"]
    filepath = "/static/jigsaw_sliced/"
    ls=[]

    for i in range(1,17):
        fpath = filepath + str(i) + "-" + id+".jpg"
        ls.append(fpath)
    random.shuffle(ls)

    print(ls)
    print("kk22")

    return jsonify(status="ok",ls=ls)

# ------------

@app.route('/edt_fed_viw', methods=['POST'])
def ed_f_v():
    pid=request.form['pid']
    db=Db()
    res=db.selectOne("select * from puzzle where puzzle_id='"+pid+"'")
    pth=res['image']
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
        return jsonify(status='ok',fname=res)
    else:
        return jsonify(status='no')
# ---------------------------------------------

#
# @app.route('/UpdateProgress', methods=['post'])
# def UpdateProgress():
#
#
#     db =Db();
#     type = request.form['type']
#     cid = request.form['cid']
#     # mark = request.form['mark']
#     time = request.form['time']
#     mem = 0
#     lan = 0
#     sol = 0
#     acc = 0
#     agil = 0
#     tot = 0
#     time=time.replace(" ","")
#     print(type)
#     print(cid)
#     print(time)
#     # print("mmmmmmmmm",mark)
#     # return jsonify(status="ok")
#     # mark = int(mark) * 4
#     # print(mark)
#     timer =time.split(':')
#     minute = int(timer[0])
#     second = int(timer[1])
#     minute = minute*60
#     time = second+minute
#     if time > 900:
#         time = 0
#     elif time >600 and time<900:
#         time = 1
#     elif time<600 and time>400:
#         time = 2
#     elif time < 400 and time > 300:
#         time = 3
#     elif  time < 300:
#         time = 4
#
#     #
#     # if mark == 0:
#     #     mem = 0
#     #     lan = 0
#     #     acc = 0
#     # elif mark == 1:
#     #     mem = 1
#     #     lan = 1
#     #     acc = 1
#     # elif mark == 2:
#     #     mem = 2
#     #     lan = 2
#     #     acc = 2
#     # elif mark == 3:
#     #     mem = 3
#     #     lan = 3
#     #     acc = 3
#     # elif mark == 4:
#     #     mem = 4
#     #     lan = 4
#     #     acc = 4
#
#     if time == 0:
#         sol = 0
#         agil= 0
#     elif time == 1:
#         sol = 1
#         agil = 1
#     elif time == 2:
#         sol = 2
#         agil = 2
#     elif time == 3:
#         sol = 3
#         agil = 3
#     elif time == 4:
#         sol = 4
#         agil = 4
#
#     # total = mem + lan + acc + sol + agil ;
#     qry = "select * from progress where pgsdate=curdate() and cid ='" + cid + "'"
#     res = db.selectOne(qry)
#     # if type == "speak":
#     #     if res is None:
#     #         tot = res['agility']+res['solving']+mem+lan+acc
#     #         tot = tot/4
#     #         qry = "insert into progress(memory,language,accuracy,total,cid,pgsdate) values ('"+str(mem)+"','"+str(lan)+"','"+str(acc)+"','"+str(tot)+"','"+cid+"',curdate())"
#     #         print(qry)
#     #         res = db.insert(qry)
#     #     else:
#     #         tot = res['agility']+res['solving']+mem+lan+acc
#     #         tot = tot / 4
#     #         qry = "update progress set memory='"+str(mem)+"',language='"+str(lan)+"',accuracy='"+str(acc)+"',total='"+str(tot)+"' where pgsdate=curdate() and cid ='"+cid+"'"
#     #         print(qry)
#     #         res = db.update(qry)
#
#     # elif type == "write":
#     #     if res is None:
#     #         tot = mem + lan + acc + agil + res['solving']
#     #         tot = tot/4
#     #         qry = "insert into progress(memory,language,accuracy,agility,total,cid,pgsdate) values ('" + str(mem) + "','" + str(lan) + "','" + str(acc) + "','"+str(agil)+"','" + str(tot) + "','" + cid + "',curdate())"
#     #         print(qry)
#     #         res = db.insert(qry)
#     #     else:
#     #         tot = mem + lan + acc + agil + res['solving']
#     #         tot = tot/4
#     #         qry = "update progress set memory='" + str(mem) + "',language='" + str(lan) + "',accuracy='" + str(acc) + "',agility = '"+str(agil)+"',total='"+str(tot)+"' where pgsdate=curdate() and cid ='" + cid + "'"
#     #         print(qry)
#     #         res = db.update(qry)
#
#     elif type == "jigsaw":
#             if res is None:
#                 # tot = res['memory']+res['language']+res['accuracy']+sol + agil
#                 # tot = tot / 4
#                 qry = "insert into progress(solving,agility,total,cid,pgsdate) values ('" + str(sol) + "','" + str(agil) + "','" + str(tot) + "','" + cid + "',curdate())"
#                 print(qry)
#                 res = db.insert(qry)
#             else:
#                 tot = res['memory']+res['language']+res['accuracy']+sol + agil
#                 tot = tot / 4
#                 qry = "update progress set solving='" + str(sol) + "',agility = '" + str(agil) + "',total='" + str(tot) + "' where pgsdate=curdate() and cid ='" + cid + "'"
#                 print(qry)
#                 res = db.update(qry)
#     # elif type == "word":
#     #     if res is None:
#     #         tot = mem + lan + acc + agil + sol
#     #         tot = tot / 4
#     #         qry = "insert into progress(memory,language,accuracy,solving,agility,total,cid,pgsdate) values ('" + str(mem) + "','" + str(lan) + "','" + str(acc) + "','" + str(sol) + "','" + str(agil) + "','" + str(tot) + "','" + cid + "',curdate())"
#     #         print(qry)
#     #         res = db.insert(qry)
#     #     else:
#     #         tot = mem + lan + acc + agil+sol
#     #         tot = tot / 4
#     #         qry = "update progress set memory='" + str(mem) + "',language='" + str(lan) + "',accuracy='" + str(acc) + "',solving='"+str(sol)+"',agility = '" + str(agil) + "',total='" + str(tot) + "' where pgsdate=curdate() and cid ='" + cid + "'"
#     #         print(qry)
#     #         res = db.update(qry)
#     # elif type == "number":
#     #         if res is None:
#     #             tot = res['memory']+res['language']+res['accuracy']+sol + agil
#     #             tot = tot / 4
#     #             qry = "insert into progress(solving,agility,total,cid,pgsdate) values ('" + str(sol) + "','" + str(agil) + "','" + str(tot) + "','" + cid + "',curdate())"
#     #             print(qry)
#     #             res = db.insert(qry)
#     #         else:
#     #             tot = res['memory']+res['language']+res['accuracy']+sol + agil
#     #             tot = tot / 4
#     #             qry = "update progress set solving='" + str(sol) + "',agility = '" + str(agil) + "',total='" + str(tot) + "' where pgsdate=curdate() and cid ='" + cid + "'"
#     #             print(qry)
#     #             res = db.update(qry)
#     return jsonify(status='ok')
#




@app.route('/UpdateProgress', methods=['post'])
def UpdateProgress():
    db =Db();
    type = request.form['type']
    cid = request.form['cid']
    time = request.form['time']
    pid = request.form['pid']
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
        time = 0
    elif time >600 and time<900:
        time = 1
    elif time<600 and time>400:
        time = 2
    elif time < 400 and time > 300:
        time = 3
    elif  time < 300:
        time = 4

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

    db.insert("insert into progress(progress_id,patient_id,date,time,puzzle_id) values('','" + cid + "',curdate(),curtime(),'" + pid + "')");
    return jsonify(status='ok')






if __name__ == '__main__':
    app.run(debug=True,port=5000,host="0.0.0.0")
