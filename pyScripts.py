#!/usr/bin/python3.6
# -*-coding:UTF-8 -*

##  IMPORTS

from numpy import cos , sin , pi, matrix, transpose , dot, log , maximum, arcsin, arctan, tan, sqrt, array
import sys
import math
import matplotlib.pyplot as plt

##  INITIALIZATIONS

# Procedure Initialise_parameters

rd = 180.0 / pi
dr = pi / 180.0

twopi = 2.0 * pi

Re = 6371008.0    #meters
c = 3*10**8          # m/s
mu 	= 3.98619056*10**14

Tearth = 86164.1 		# s
w_earth = 2*pi/Tearth		#Earth’s rotational velocity, rad /s

phi_min = 1

# precession of the orbital plane – related to Omega

w_precession_deg 		= 5.0  	 				#deg/day
w_precession			= dr  * w_precession_deg / Tearth 	# rad/s

# Earth’s paramaters

phi_g0_deg 	= -15.5 				# initial angle of greenwhich meridian, deg 
phi_g0 		= dr * phi_g0_deg		# rad

# time initialization

nDays = 2      # 2 days of simulations

dt = 60.0     				# seconds

Nmax = int(nDays * Tearth / dt) + 1 	# number of time steps


def decoder():
    
    code = int(sys.argv[-1])
    
    global dt
    dt = float(sys.argv[-2])
    
    global nDays 
    nDays = int(sys.argv[-3])
    
    global Nmax 
    Nmax = int(nDays * Tearth / dt) + 1
    
    if code == 4:
        G_645()
    elif code == 5:
        G_850()
    elif code == 7:
        getDAndPhi()
    elif code == 8:
        plotAz_Elv()
    elif code == 9:
        plotAz_Elv()
    elif code == 10:
        plotD_SAT_ES()
    elif code == 11:
        plotGs()
    elif code == 20:
        getPath()
    else:
        print("PARSING ERROR")

##  GAINS
        
def dotproduct(v1, v2):
    return sum((a*b) for a, b in zip(v1, v2))

def length(v):
    return math.sqrt(dotproduct(v, v))

def angle(v1, v2):
    return math.acos(dotproduct(v1, v2) / (length(v1) * length(v2)))
 
def getDAndPhi():
    
    nodeAlt = float(sys.argv[1])
    nodeLat = float(sys.argv[2])
    nodeLong = float(sys.argv[3])
    
    sensorAlt = float(sys.argv[4])
    sensorLat = float(sys.argv[5])
    sensorLong = float(sys.argv[6])
    
    xNode = (Re+nodeAlt)*cos(nodeLat*pi/180)*cos(nodeLong*pi/180)
    yNode = (Re+nodeAlt)*cos(nodeLat*pi/180)*sin(nodeLong*pi/180)
    zNode = (Re+nodeAlt)*sin(nodeLat*pi/180)
    
    xSensor = (Re+sensorAlt)*cos(sensorLat*pi/180)*cos(sensorLong*pi/180)
    ySensor = (Re+sensorAlt)*cos(sensorLat*pi/180)*sin(sensorLong*pi/180)
    zSensor = (Re+sensorAlt)*sin(sensorLat*pi/180)
    
    node = array([xNode,yNode,zNode])
    sensor = array([xSensor,ySensor,zSensor])
    
    print(length(node-sensor))
    print(angle(node,sensor-node))
    
def G_645():
    
    frequency = float(sys.argv[1])
    D = float(sys.argv[2])
    phi = float(sys.argv[3])
    
    '''
    if D/lmbda >= 50 :
        
        phi_min = maximum(1,100*lmbda/D)
    
    else :
        
        phi_min = maximum(2,114*((D/lmbda)**(-1.06)))
    '''
    
    if phi < 48 and phi > phi_min :
    
        print(32 - 25 * log(phi))
    
    else :
    
        print(-10)
    
def G_850():
    
    frequency = float(sys.argv[1])
    D = float(sys.argv[2])
    phi = float(sys.argv[3])
    
    lmbda = c/frequency
    
    if phi < 20 and phi > 1 :
    
        print(29 - 25 * log(phi))
    
    else :
    
        print(-3.5)
        
def plotGs():
    
    frequency = float(sys.argv[1])
    D = float(sys.argv[2])
    
    def G850(frequency,D,phi):
        
        lmbda = c/frequency
    
        if phi < 20 and phi > 1 :
    
            return (29 - 25 * log(phi))
    
        else :
    
            return -3.5
    
    def G645(frequency,D,phi):

        lmbda = c/frequency
    
        '''
        if D/lmbda >= 50 :
            
            phi_min = maximum(1,100*lmbda/D)
        
        else :
            
            phi_min = maximum(2,114*((D/lmbda)**(-1.06)))
        '''
    
        if phi < 48 and phi > phi_min :
    
            return (32 - 25 * log(phi))
    
        else :
    
            return (-10)
    
    X = []
    G_850 = []
    G_645 = []
    
    phi = 0
    dphi = 0.1
    
    while phi < 180 :
        X.append(phi)
        G_850.append(G850(frequency,D,phi))
        G_645.append(G645(frequency,D,phi))
        phi = phi + dphi
    
    plt.figure("Plots of gain using 850 and 645 recommandations")
    plt.plot(X,G_850)
    plt.plot(X,G_645)
    plt.title("comparison between 850 and 645 recommandations")
    plt.xlabel("phi angle")
    plt.ylabel("gain G")
    plt.show()
        
##  PLOTS
    
def getCoorNGS_t(altitude, nu0_deg, omega0_deg, inc_deg, time):

    nu_0 = dr * nu0_deg	# rad
    
    inclin_rad 	= dr * inc_deg	# rad
    
    omega0	= dr * omega0_deg	# rad
    
    a_sat 	= Re + altitude 
    
    period   = twopi * math.sqrt(a_sat**3/mu)
    
    w_sat = twopi / period 	# rad / s  
    
    # Procedure sat_coordinates (Input: time and the constrants // Output xs, ys, zs)
    # Begin
    
    # precession of the orbital plane
    omega = omega0 - w_precession * time 
    
    # satellite coordinates
    nu_sat_time = w_sat * time + nu_0	# anomaly with time,  nu(t)
    
    cos_nu = cos(nu_sat_time)
    sin_nu = sin(nu_sat_time)
    
    #calculation of Phi_s
    
    xx = cos_nu
    yy = cos(inclin_rad) * sin_nu
    
    phi_s = math.atan2(yy,xx) + omega		#delete the omeg_rad from your program
    
    # calculation of theta_s
    
    sin_phi_s = sin(phi_s)
    tan_inc = tan(inclin_rad)
    theta_s = math.atan(tan_inc * sin_phi_s)
    
    # calculation of xs, ys, zs
    
    cos_theta_s = cos(theta_s)
    
    xs = a_sat * cos(phi_s) * cos_theta_s
    ys = a_sat * sin(phi_s) * cos_theta_s
    zs = a_sat * sin(theta_s)
    
    # end procedure that calculated the (xs, ys, zs, th_s , phi_s)
    
    return array([xs,ys,zs,theta_s,phi_s])
    
def getCoorES_t(latitude_es_deg,longitude_es_deg,time):
    
    # Procedure ES_coordinates (input: time /// output theta_es, phi_es)
    # Begin
    
    latitude_es= dr * latitude_es_deg
    
    longitude_es = dr * longitude_es_deg
    
    # greenwhich angle
    phi_g = w_earth * time + phi_g0	# angle of the greenwhich meridian with aries point
    
    # converting to (theta, phi)
    theta_es = latitude_es			# radians
    phi_es = longitude_es + phi_g		# radians
    
    # end procedure ES angular coordinate

    return array([theta_es,phi_es])
    
def getCoorNGSinES_t(latitude_es_deg,longitude_es_deg,altitude, nu0_deg, omega0_deg, inc_deg, time):
    
    a_sat 	= Re + altitude 
    
    es_t = getCoorES_t(latitude_es_deg,longitude_es_deg,time)
    sat_t = getCoorNGS_t(altitude, nu0_deg, omega0_deg, inc_deg, time)
    
    th_es = es_t[0]
    ph_es = es_t[1]
    xs = sat_t[0]
    ys = sat_t[1]
    zs =  sat_t[2]
    th_s = sat_t[3]
    ph_s =  sat_t[4]
    
    # calculate below the new sat coordiantes from the ES point of view
    
    ctp = cos(th_es)
    stp =  sin(th_es)
    
    cpp = cos(ph_es)
    spp = sin(ph_es)
    
    #first step conversion
    
    # cosine of theta_satellite
    cts = cos(th_s)
    sts = sin(th_s)
    
    #
    
    x1 = a_sat * cos(ph_s) * cts
    y1 = a_sat * sin(ph_s) * cts
    z1 = a_sat * sts
    
    # 
    
    temp1 = cpp * x1 + spp * y1
    
    # here are the satellite coordinate in the ES reference system
    
    xs1 =  stp * temp1 - ctp * z1
    ys1 =-spp * x1 + cpp * y1
    zs1 = ctp * temp1 + stp* zs - Re
    
    # end procedure

    return array([xs1,ys1,zs1])
    
def plotAz_Elv():

    satAlt = float(sys.argv[1])
    satAnomaly = float(sys.argv[2])
    satOmega = float(sys.argv[3])
    satInc = float(sys.argv[4])
    esLat = float(sys.argv[5])
    esLong = float(sys.argv[6])

    time = 0

    T=[]
    Az=[]
    Elv=[]
    
    for i in range(Nmax):

        satInES_t = getCoorNGSinES_t(esLat,esLong,satAlt,satAnomaly,satOmega,satInc,time)
        
        xs1 = satInES_t[0]
        ys1 = satInES_t[1]
        zs1 = satInES_t[2]
        
        az = pi - math.atan2(ys1, xs1) 	# 4 quadrant
        elv = math.atan2(zs1,math.sqrt(xs1**2 + ys1**2))  # 4 quadrant
        
        az_deg = rd*az
        elv_deg = rd*elv

        Az.append(az_deg)
        Elv.append(elv_deg)
        T.append(time)
        
        time = time + dt
    
    plt.figure("Elevation as a function of azimuth")
    plt.plot(Az,Elv)
    plt.title("Plot of elevation as a function of azimuth")
    ax=plt.gca()
    ax.set_xlim([0,360])
    ax.set_ylim([0,90])
    plt.show()
    
def plotD_SAT_ES():
    
    satAlt = float(sys.argv[1])
    satAnomaly = float(sys.argv[2])
    satOmega = float(sys.argv[3])
    satInc = float(sys.argv[4])
    esLat = float(sys.argv[5])
    esLong = float(sys.argv[6])
    
    time = 0

    T=[]
    D=[]
    
    for i in range(Nmax):
        
        satInES_t = getCoorNGSinES_t(esLat,esLong,satAlt,satAnomaly,satOmega,satInc,time)
        
        xs1 = satInES_t[0]
        ys1 = satInES_t[1]
        zs1 = satInES_t[2]
        
        d = math.sqrt(xs1**2 + ys1**2 + zs1**2)
        
        T.append(time)
        D.append(d)
        
        time = time + dt
    
    plt.figure("Distance between the satellite and the earth station")
    plt.plot(T,D)
    plt.title("distance between es and sat")
    plt.xlabel("time")
    plt.ylabel("distance")
    plt.show()

def getPath():

    altitude = float(sys.argv[1])
    anomaly = float(sys.argv[2])
    omega = float(sys.argv[3])
    inc = float(sys.argv[4])

    time = 0
    
    res = "" 

    for i in range(Nmax):
        
        coor_t = getCoorNGS_t(altitude, anomaly, omega, inc, time)

        th_s = coor_t[3]
        ph_s = coor_t[4]
        
        th_s_deg = rd*th_s
        ph_s_deg = rd*ph_s - w_earth*time
        
        res = res + str(th_s_deg) + " "+str(ph_s_deg) + " "
        
        time = time + dt
        
    print(res)
    
##  START
        
decoder()
