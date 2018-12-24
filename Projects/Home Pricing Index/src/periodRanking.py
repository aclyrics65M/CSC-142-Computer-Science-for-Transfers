"""
FileName: periodRanking.py
Description:
Author: Faraz Mamaghani
Date: 02/28/2018

"""

import indexTools
import collections
import sys

# Define the function quarter data
def quarter_data(data, year, qtr):
    """
    The quarter_data function takes in the parameters including year and quarter
    and creates a list of tuples sorted from high value HPI to low value HPI.
    :param data, year, str
    :return: A list of (region, HPI) tuples sorted
     from high value HPI to low value HPI.
    """

    # Create a new dictionary and a stored list
    dictionaryList = dict()
    sortedList = []

    for inValue in data:

        # Create a quarter data object
        QuarterHPIObject = data[inValue]

        # Length of the quarter HPI Object
        lengthQuarterHPIObject = len(QuarterHPIObject)

        # For loop
        for index in range(0, lengthQuarterHPIObject):

            # The for loop proceeds through the list to find a matching year
            if QuarterHPIObject[index].year == year and QuarterHPIObject[index].qtr == qtr:

                # Check to see if the in value exists in the dictionary
                # If yes, append it to the dictionary list
                if inValue in dictionaryList:
                    dictionaryList[inValue].append(QuarterHPIObject[index])
                else:
                    dictionaryList[inValue] = [QuarterHPIObject[index]]

    # Print out the entire contents of the dictionary
    # print("Dictionary", dictionaryList)

    # Place elements of dictionary into a sorted List
    for key in dictionaryList:
        dictElement = dictionaryList[key]

        for mValue in dictElement:
            sortedList.append((key, mValue.index))
    sortedList.sort(key = lambda tuple: tuple[1])

    sortedListHighToLow = list(reversed(sortedList))

    # Return the statement
    return sortedListHighToLow


# Define the function annual data
def annual_data(data, year):
    """
    The annual_data function takes in the parameters including year and quarter
    and creates a list of tuples sorted from high value HPI to low value HPI.
    :param data, year
    :return: A list of (region, HPI) tuples sorted from
     high value HPI to low value HPI.
    """

    # Create a new ditionary
    dictionaryList = dict()
    sortedList = []

    for inValue in data:

        # Create an annual data object
        AnnualHPIObject = data[inValue]

        # Length of the annual HPI object
        lengthAnnualHPIObject = len(AnnualHPIObject)

        # For loop
        for index in range(0, lengthAnnualHPIObject):

            # The for loop proceeds through the list to find a matching year
            if AnnualHPIObject[index].year == year:
                # Check to see if the in value exists in the dictionary
                # If yes, append it to the dictionary list
                if inValue in dictionaryList:
                    dictionaryList[inValue].append(AnnualHPIObject[index])
                else:
                    dictionaryList[inValue] = [AnnualHPIObject[index]]

    # Print out the entire contents of the dictionary
    print("Dictionary", dictionaryList)

    # Place elements of dictionary into a sorted List
    for key in dictionaryList:
        dictElement = dictionaryList[key]

        for mValue in dictElement:
            sortedList.append((key, mValue.index))
    sortedList.sort(key = lambda tuple: tuple[1])

    sortedListHighToLow = list(reversed(sortedList))

    # Return the statement
    return sortedListHighToLow


def main():
    """
    docstring for main function here

    :return:
    """

    # Prompt user to input filename, region variable, and year variable
    # filenameVariable = input("Filename: ")
    # regionVariable = input("State abbreviation or ZIP Code: ")
    # yearVariable = input("Year: ")

    # Check to see if the ZIP exists in the filename
    # if "ZIP" in filenameVariable:
    #     data = indexTools.read_zip_house_price_data(filenameVariable)
    #     print("ZIP Data")
    #     print()

    dataFilesPeriodRanking = []
    filename = ''
    year = ''

    if len(sys.argv) != 3:
        filename = input("Enter region-based house price index filename: ")
        year = int(input("Enter year of interest for house price: "))
    else:
        filename = sys.argv[1]
        year = int(sys.argv[2])

    dataFiles = indexTools.read_state_house_price_data(filename)
    dataFilesAnnualized = indexTools.annualize(dataFiles)
    pythonList = []

    for key in dataFilesAnnualized:
        entry = dataFilesAnnualized[key]

        for entryValue in entry:
            if entryValue.year == year:
                pythonList.append((key, entryValue))

    pythonList = sorted(pythonList, key=lambda x:x[1].index,reverse=True)

    print(year, " Annual Ranking")
    print("The Top 10:")
    for i,listValue in enumerate(pythonList[:10]):
        print(i+1, " : (", listValue[0], ", ", listValue[1].index, ")")

    pythonList.reverse()
    lowest = pythonList[:10]
    lowest.reverse()

    print("The Bottom 10:")
    for i,reverseValue in enumerate(lowest):
        print(i+1, " : (", reverseValue[0], ", ", reverseValue[1].index, ")")




if __name__ == '__main__':
    # main runs only when directly invoking this module
    main()

# end of program