#!/usr/bin/ python3
# -*- coding: utf-8 -*-
from zipfile import *  # @UnusedWildImport

import json
import sys
import os

def createZip(dataJson):
    data = json.loads(dataJson)
    zipFileName = data['zipFileName']
    zipFileList = data['fileList']
    zipTotalNum = len(zipFileList)
    myzip = ZipFile(zipFileName, 'w', ZIP_DEFLATED)
    for i in range(zipTotalNum):
        myzip.write(zipFileList[i], os.path.basename(zipFileList[i]))
        print('%d' % (i + 1) + "/" + '%d' % zipTotalNum)
    myzip.close()

if __name__ == '__main__':
    a = sys.argv
    createZip(a[1])