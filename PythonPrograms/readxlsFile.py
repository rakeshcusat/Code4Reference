#!/usr/bin/python

import optparse  ###Option parser
try:
    import xlrd   ###Need to make sure xlrd library is available
except ImportError:
    print 'Could not find package "xlrd". Try installing it first'
    print ''
    print 'Halting.'
    sys.exit(1)

def formatDataTypeString(dataType):
    # Cell Types: 0=Empty, 1=Text, 2=Number, 3=Date, 4=Boolean, 5=Error, 6=Blank
    formatString =":"
    if dataType == 0:
        formatString +=" Empty :"
    elif dataType == 1:
        formatString +=" Text :"
    elif dataType == 2:
            formatString +=" Number :"
    elif dataType == 3:
            formatString +=" Date :"
    elif dataType == 4:
            formatString +=" Boolean :"
    elif dataType == 5:
            formatString +=" Error :"
    elif dataType == 6:
            formatString +=" Blank :"

    return formatString


def metaData(xlFile):
    """This method takes one argument which is xl file name. It display the 
    meta-data of the xl file. It displays sheet names present in the file 
    and the content type.
    """
    book = xlrd.open_workbook(xlFile) 
    print("#########sheet Names#########") 
    sheetNames = ""
    for sheet in book.sheet_names():
        sheetNames += str(sheet) + " "
    print(sheetNames)

    sheets = book.sheets()
    for sheet in sheets:
       
        print("=====" + str(sheet.name) + "(" + str(sheet.nrows) + ", " + str(sheet.ncols) + ")=====")
        if sheet.ncols > 0:
            curCol = 0;
            colsType = ""
            while curCol < sheet.ncols:
                colsType +=formatDataTypeString(sheet.cell_type(0,curCol))   
                curCol +=1         
            print("First row cell Type " + colsType)
        else:
            print("Sheet is empty")

def showData(xlFile, sheetName):
    """This method takes two arguments xlFile name and SheetName. 
    It display data if it is present in the provided sheet"""
    book = xlrd.open_workbook(xlFile)
    if sheetName:
        sheet = book.sheet_by_name(sheetName)
    else:
        sheet = book.sheet_by_index(0)
    if sheet.nrows > 0:
        for i in xrange(sheet.nrows):
            print(sheet.row_values(i))
    else:
        print ("Sheet is empty")

def main():
    ##Argument parsing

    parser = optparse.OptionParser(usage = "%prog filename [options] \n" )
    parser.add_option("-m","--metadata",
                        action="store_true",
                        default = False,
                        dest = "metaData",
                        help = "Meta information of XL sheet")
    parser.add_option("-d","--showData",
                        action="store_true",
                        default = False,
                        dest = "showData",
                        help = "Show Data of specified sheet otherwise show data from first sheet")
    parser.add_option("-s","--sheet",
                        dest = "sheetName",
                        help = "Sheet name whose data you want to see. This option should be used with -s(--showData) option")
    
    (options, args) = parser.parse_args()
    #print (args)
    if len(args) != 1:
        parser.print_help()
        return -1
    else:
        if not args[0]:
            print("Please provide file name")
            print("")
            parser.print_help()
            return -1
        else: 
            if options.metaData:
                metaData(args[0])
            elif options.showData:
                showData(xlFile=args[0], sheetName=options.sheetName)
            else:
                parser.print_help()

if __name__ == "__main__":
    main()
