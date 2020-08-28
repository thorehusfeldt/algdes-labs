# Parse input data to some data structure
def parseFromStream(stream):
    fileData = ''
    for line in stream:
        fileData = fileData + line
    parseFromString(fileData)

def parseFromString(str):
    pass
