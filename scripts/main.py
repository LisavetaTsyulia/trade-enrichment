

def writeToFile(file, header, trades, num):
    with open(file, 'w') as f:
         f.write(header)
         for i in range(num):
            f.writelines(trades)
            f.write('\n')

def getsample(file):
    with open(file, 'r') as f:
        lines = f.readlines()
        return lines[0], lines[1:]

header, trades = getsample('src/test/resources/trade100k.csv')

num = 1000
writeToFile('src/test/resources/trades' + str(num) + '.csv', header, trades, num)
