"""
FileName: indexTools.py
Description:
Author: Faraz Mamaghani
Date: 02/28/2018
"""

import collections
import sys

#program imports, types, and function definitions here...

# Define the tuples QuarterHPI and AnnualHPI
QuarterHPI = collections.namedtuple("QuarterHPI", ("year", "qtr", "index"))
AnnualHPI = collections.namedtuple("AnnualHPI", ("year", "index"))

# Define the function read state house price
def read_state_house_price_data(filepath):
    """
    Reads in the state house price data of the filepath
    :param filepath: the file name to be read in the function
    :return: dictionary
    """

    # Create a dictionary
    pythonDict = dict()
    skipLine = True

    with open(filepath) as data_file:
        # for loop to read each line within the filepath
        for line in data_file:
            data = line.strip().split('\t')

            if skipLine:
                skipLine = False
                continue

            try:
            # Create an object of QuarterHPI
                QuarterHPIObject = QuarterHPI(int(data[1]), float(data[2]), float(data[3]))
                # If statement if something exists in the state
                if data[0] in pythonDict:
                    pythonDict[data[0]].append(QuarterHPIObject)
                else:
                    pythonArray = [QuarterHPIObject]
                    pythonDict[data[0]] = pythonArray
                # dataInstance = QuarterHPI("1997", "3", "1")
            except ValueError:
                print("warning: data unavailable in original source.")
                print(line)



    # Return the contents of the python dictionary
    return pythonDict
    '''
    for i in pythonDict:
        print(pythonDict[i])
    print()
    '''

# Define the function read zip house price data
def read_zip_house_price_data(filepath):
    """
    Reads in the zip house price data of the filepath
    :param filepath: the file name to be read in the function
    :return: dictionary
    """

    # Create the list of variables for count and uncount
    uncountNumber = 0
    countNumber = 0
    status = True
    skipFirstLine = True

    # Create a dictionary
    sampleDictionary = dict()

    with open(filepath) as data_file:

        for pythonLine in data_file:
            if skipFirstLine:
                skipFirstLine = False
                continue

            pythonData = pythonLine.strip().split('\t')

            for index in pythonData:
                if index == ".":
                    uncountNumber = uncountNumber + 1
                    status = False
                    # print("warning: data unavailable in original source.")
            if status:
                countNumber = countNumber + 1

                # Create an object of AnnualHPI
                AnnualHPIObject = AnnualHPI(int(pythonData[1]), float(pythonData[3]))

                # If statement if something exists in the state
                if pythonData[0] in sampleDictionary:
                    sampleDictionary[pythonData[0]].append(AnnualHPIObject)
                else:
                    sampleArray = [AnnualHPIObject]
                    sampleDictionary[pythonData[0]] = sampleArray

            # Reset status to true
            status = True

    # Return the contents of the sample dictionary
    print("count number: " + str(countNumber) + " uncount number: " + str(uncountNumber))
    return sampleDictionary

# Define the function index range
def index_range(data, region):
    """
    Returns the index range of a given data and region
    :param A dictionary mapping regions to lists of *HPI objects and a region name. The
    objects may be either QuarterHPI or AnnualHPI objects.
    :return: A tuple of the *HPI objects which are the low and high index values respectively.
    """

    # Create an index range array and minimum and maximum values
    indexRangeArray = data[region]
    minimum = 1000
    maximum = -100

    # For loop to check for minimum and maximum values
    for indexNumber in range(len(indexRangeArray)):

        # If statement
        if indexRangeArray[indexNumber].index < minimum:
            minimum = indexRangeArray[indexNumber].index

            # Set the low value to this minimum
            lowerIndexBound = indexNumber

        if indexRangeArray[indexNumber].index > maximum:
            maximum = indexRangeArray[indexNumber].index

            # Set the high value to this maximum
            upperIndexBound = indexNumber


    # Return statement
    return data[region][lowerIndexBound], data[region][upperIndexBound]


# Define the function print range
def print_range(dataVariable, regionVariable):
    """
    Prints the low and high values (year/quarter/index) for the input region
    :param Data, region. A dictionary mapping regions to lists of *HPI objects and a region name.
    :return: None.
    """

    # Print out the values
    print("Region: ", regionVariable)

    minimumValue = index_range(dataVariable, regionVariable)[0]
    maximumValue = index_range(dataVariable, regionVariable)[1]

    # Print out the low and high values
    print("Low: year/quarter/index: ", minimumValue.year, " ", minimumValue.qtr, " ", minimumValue.index)
    print("High: year/quarter/index: ", maximumValue.year, " ", maximumValue.qtr, " ", maximumValue.index)

# Define the function print ranking
def print_ranking(data, heading = "Ranking"):
    """
    Reads in the data and prints out the sorted list
    :param data, heading = "Ranking"
    :return: None.
    """

    # Create a printRanking array
    printRanking = []

    # For loop to check the values in the data
    for inValue in data:
        # Create an unsorted list
        unSortedList = data[inValue]

        # For loop to append the values
        for indexVal in unSortedList:
            printRanking.append(inValue, indexVal.index)

    # Implement a sorting method
    printRanking.sort(inValue = lambda tuple: tuple[1])

    # Reversing the list
    reverseList = list(reversed(printRanking))

    # Print the heading
    print(heading)
    print("The Top 10: ")

    for v in range(0,10):
        print( (v+1), ": ", reverseList[v])

    print() # Blank space
    print() # Blank space

    print("The Bottom 10: ")
    for m in range(10):
        print( (m + 1), ": ", printRanking[10 - m])

# Define the function annualize
def annualize(data):
    """
    Reads in the data input and it averages those objects
    to create the lists of AnnualHPI objects.
    :param data
    :return: A dictionary mapping regions to lists of AnnualHPI objects.
    """

    # Create an empty dictionary
    pythonDictionary = dict()

    # Initial parameters
    averageYear = 0
    numberQuarters = 0

    # For loop for analyzing the elements of each region
    for instanceRegion in data:

        regionalData = data[instanceRegion]

        # Initialize the current year
        presentYear = 0

        # Length of regionalData
        lengthRegionalData = len(regionalData)

        # For loop to scan each value of the regionalData
        for x in range(0, lengthRegionalData):

            sampleHPI = regionalData[x]

            # If statement
            if presentYear == sampleHPI.year:
                # Increment the number of quarters by one
                numberQuarters = numberQuarters + 1

                # Increment the value of the averageYear by the index of the sampleHPI
                averageYear = float(averageYear) + float(sampleHPI.index)

                testValue = len(regionalData) - 1;

                if testValue == x:
                    # Divide the yearly average by the number of quarters
                    averageYear = averageYear / numberQuarters

                    # Create an annual object
                    annualObject = AnnualHPI(presentYear, averageYear)

                    # Check to see if key value exists
                    # If so, append it to the pythonDictionary
                    if instanceRegion in pythonDictionary:
                        pythonDictionary[instanceRegion].append(annualObject)
                    else:
                        pythonDictionary[instanceRegion] = [annualObject]

            else:
                # If the present year does not equal 0
                if presentYear != 0:
                    averageYear = averageYear / numberQuarters

                    # Recreate an annual object
                    annualObject = AnnualHPI(presentYear, averageYear)

                    # If else to see if instanceRegion is in pythonDictionary
                    if instanceRegion in pythonDictionary:
                        pythonDictionary[instanceRegion].append(annualObject)
                    else:
                        pythonDictionary[instanceRegion] = [annualObject]

                # Set the present year to the year of the sampleHPI
                presentYear = sampleHPI.year

                # Set the average year equal to the index of the sampleHPI.
                averageYear = sampleHPI.index

                # Set the quarter number value to 1
                numberQuarters = 1

    return pythonDictionary


def main():
    """
    docstring for main function here
    :return:
    """

    filename = ''
    if(len(sys.argv) == 1):
        filename = input("Enter house price index file: ")
    else:
        filename = sys.argv[1]

    dataFiles = read_state_house_price_data(filename)
    dataFilesAnnualized = annualize(dataFiles)


    regions = []
    if(len(sys.argv) > 2):
        regions = sys.argv[2:]
    else:
        inregion = 'tmp'
        while(inregion != ''):
            inregion = input("Next region of interest( Hit ENTER to stop): ")
            if inregion != '':
                regions.append(inregion)

    print('='*72)
    # Print out the
    for iValue in regions:

        if len(iValue) == 2:
            region_data = dataFiles[iValue]
            min_index = min(region_data, key=lambda x: x.index)
            max_index = max(region_data, key=lambda x: x.index)
            print("Region: ", iValue)
            print("Low: year/quarter/index: ", min_index.year, " / ", min_index.qtr, " / ", min_index.index)
            print("High: year/quarter/index: ", max_index.year, " / ", max_index.qtr, " / ", max_index.index)

        print("Region: ", iValue)
        region_data_annualized = dataFilesAnnualized[iValue]
        min_index_Annualized = min(region_data_annualized, key=lambda x:x.index)
        max_index_Annualized = max(region_data_annualized, key=lambda x:x.index)
        print("Low: year/quarter/index: ", min_index_Annualized.year, " / ", min_index_Annualized.index)
        print("Low: year/quarter/index: ", max_index_Annualized.year, " / ", max_index_Annualized.index)

        print("Annualized Index Values for ", iValue)

        for listValues in region_data_annualized:
            print(listValues)

if __name__ == '__main__':
    # main runs only when directly invoking this module
    main()

# end of program
