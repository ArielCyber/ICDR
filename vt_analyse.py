import os
import pandas as pd
import time
import requests
import json
from vtapi3 import *

# download the results from VirusTotal by file id

key = "your key"
vt_files = VirusTotalAPIFiles(key)
list_res = []
fid = ''
df = pd.read_csv('Malicious JPEG SHA256 VirusTotal.csv',sep=',')
for index, row in df.iterrows():
    print('working on: '+ str(index))
    fid = row.get('id')
    try:
        res = vt_files.get_report(fid)
    except VirusTotalAPIError as err:
        print(err, err.err_code)
    if vt_files.get_last_http_error() == vt_files.HTTP_OK:
        res = json.loads(res)
        data = {}
        data['name'] = row.get('name')
        data['sha256'] = res.get('data', {}).get('attributes', {}).get('sha256')
        data['size'] = res.get('data', {}).get('attributes', {}).get('size')
        data['type_extension'] = res.get('data', {}).get('attributes', {}).get('type_extension')
        try:
            data['threat type'] = res.get('data', {}).get('attributes', {}).get('popular_threat_classification',{}).get('suggested_threat_label')
        except:
            data['threat type'] = ''
        try:
            data['file_type'] = res.get('data', {}).get('attributes', {}).get('trid')[0].get('file_type')
        except:
            data['file_type'] = ''
        data['malicious'] = res.get('data', {}).get('attributes', {}).get('last_analysis_stats', {}).get('malicious')
        data['undetected'] = res.get('data', {}).get('attributes', {}).get('last_analysis_stats', {}).get('undetected')
        data['unsupported'] = res.get('data', {}).get('attributes', {}).get('last_analysis_stats', {}).get('type-unsupported')
        list_res.append(data)
        df1 = pd.DataFrame(list_res)
        df1.to_csv('vt_jpg.csv',sep=',')
    else:
        print('HTTP Error [' + str(vt_files.get_last_http_error()) +']')
    print()
