from numpy import cos , sin , pi, matrix, transpose , dot, log , maximum, arcsin, arctan, sqrt, array
from math import atan2
import sys
import math
import matplotlib.pyplot as plt

Re = 6371008
c = 3*10**8
phi_min = 1
we = 2*pi/86164.09

def dotproduct(v1, v2):
    return sum((a*b) for a, b in zip(v1, v2))

def length(v):
    return math.sqrt(dotproduct(v, v))

def angle(v1, v2):
    return math.acos(dotproduct(v1, v2) / (length(v1) * length(v2)))

def getCoorES_t(satLat,satLong,time):
    
    phiG = time*we
    xG = Re*cos(satLat*pi/180)*cos(satLong*pi/180)
    yG = Re*cos(satLat*pi/180)*sin(satLong*pi/180)
    zG = Re*sin(satLat*pi/180)
    
    return array([xG*cos(phiG)-yG*sin(phiG),xG*sin(phiG)+yG*cos(phiG),zG])
    
def getCoorSAT_t(altitude,satLat,satLong,period,inc,time):
    
    a = altitude + Re
    ws = 2*pi/period
    
    X = matrix([a*cos(satLat*pi/180)*cos(satLong*pi/180),a*cos(satLat*pi/180)*sin(satLong*pi/180),a*sin(satLat*pi/180)])

    Plong = matrix([[cos(satLong*pi/180),-sin(satLong*pi/180),0],[sin(satLong*pi/180),cos(satLong*pi/180),0],[0,0,1]])
    Plat = matrix([[cos(satLat*pi/180),0,-sin(satLat*pi/180)],[0,1,0],[sin(satLat*pi/180),0,cos(satLat*pi/180)]])
    Pinc = matrix([[1,0,0],[0,cos((inc-90)*pi/180),-sin((inc-90)*pi/180)],[0,sin((inc-90)*pi/180),cos((inc-90)*pi/180)]])
    Pt = matrix([[cos(ws*time),0,-sin(ws*time)],[0,1,0],[sin(ws*time),0,cos(ws*time)]])
    
    Xt = dot(Plong.transpose(),dot(Plat.transpose(),dot(Pinc.transpose(),dot(Pt.transpose(),dot(Pinc,dot(Plat,dot(Plong,X.transpose())))))))
    
    x_t = Xt.item(0,0)
    y_t = Xt.item(1,0)
    z_t = Xt.item(2,0)
    
    return array([x_t,y_t,z_t])

def plotAz_Elv():

    satAlt = float(sys.argv[1])
    satLat = float(sys.argv[2])
    satLong = float(sys.argv[3])
    satPer = float(sys.argv[4])
    satInc = float(sys.argv[5])
    esLat = float(sys.argv[6])
    esLong = float(sys.argv[7])
    #Nmax = int(sys.argv[8])

    time = 0
    #dt = satPer/(10*Nmax)
    dt = 180
    T=[]
    Az=[]
    Elv=[]
    
    #for i in range(30*Nmax):
    for i in range(1036800):

        es = getCoorES_t(satAlt,satLong,time)
        sat = getCoorSAT_t(satAlt,satLat,satLong,satPer,satInc,time)
        Re = 6371008
        a = satAlt + Re
        ws = 2*pi/satPer
    
        X = matrix([a*cos(satLat*pi/180)*cos(satLong*pi/180),a*cos(satLat*pi/180)*sin(satLong*pi/180),a*sin(satLat*pi/180)])

        Plong = matrix([[cos(satLong*pi/180),-sin(satLong*pi/180),0],[sin(satLong*pi/180),cos(satLong*pi/180),0],[0,0,1]])
        Plat = matrix([[cos(satLat*pi/180),0,-sin(satLat*pi/180)],[0,1,0],[sin(satLat*pi/180),0,cos(satLat*pi/180)]])
        Pinc = matrix([[1,0,0],[0,cos((satInc-90)*pi/180),-sin((satInc-90)*pi/180)],[0,sin((satInc-90)*pi/180),cos((satInc-90)*pi/180)]])
        Pt = matrix([[cos(ws*time),0,-sin(ws*time)],[0,1,0],[sin(ws*time),0,cos(ws*time)]])
    
        Xt = dot(Plong.transpose(),dot(Plat.transpose(),dot(Pinc.transpose(),dot(Pt.transpose(),dot(Pinc,dot(Plat,dot(Plong,X.transpose())))))))
    
        x_t = Xt.item(0,0)
        y_t = Xt.item(1,0)
        z_t = Xt.item(2,0)
    
        T.append(time)

        #Az.append(angle(es,sat)*180/pi)
        Az.append((pi - atan2(y_t,x_t))*180/pi)
        if atan2(z_t,sqrt(x_t*x_t+y_t*y_t))*180/pi >=0:
            Elv.append(atan2(z_t,sqrt(x_t*x_t+y_t*y_t))*180/pi)
        else:
            Elv.append(0)
        #if angle(sat-es,-es)*180/pi -90 >=0 :
            #Elv.append(atan2(z,sqrt(x*x+y*y))*180/pi)
        #else :
            #Elv.append(0)

        time = time + dt
        i += 180
    
    plt.figure("Plot of elevation as a function of azimuth")
    plt.plot(Az,Elv)
    plt.title("Elevation as a function of azimuth")
    plt.show()

'''print(getCoorES_t(50.2,-0.2,86400))
print(getCoorSAT_t(10350000,50.2,-0.2,21518.33,45,86400))'''

plotAz_Elv()