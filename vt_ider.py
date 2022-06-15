import os
import pandas as pd
import time
import requests
import json
from vtapi3 import *

# upload the files to VirusTotal and create a list of the files and their ids

key = "your key"
base_path = "."
vt_files = VirusTotalAPIFiles(key)
list_res = []

for root, subdirs, files in os.walk(base_path):
    for filename in files:
        try:
            vt_files.upload(base_path+"/"+filename)
        except VirusTotalAPIError as err:
            print(err, err.err_code)
        if vt_files.get_last_http_error() != vt_files.HTTP_OK:
            print('HTTP Error [' + str(vt_files.get_last_http_error()) +']')

for root, subdirs, files in os.walk(base_path):
    for filename in files:
        try:
            res = {}
            res['name'] = filename
            res['id'] = vt_files.get_file_id(base_path+"/"+filename)
            list_res.append(res)
            df = pd.DataFrame(list_res)
            df.to_csv('vt_jpg_id.csv',sep=',')
        except VirusTotalAPIError as err:
            print(err, err.err_code)
        
