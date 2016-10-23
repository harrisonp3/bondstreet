# Data Aggregator for Bond Street

## Overview
This program calculates a company's "P-score" by gathering some publicly available data about it. 
First, using the twitter4j API, we grab the ID used to identify the Twitter account; next we use
the TwitterCounter API to get a 1 month follower projection - a proxy for sales. Additionally, we
connect to the Watson API from IBM to query news coverage about the company and aggregate a sentiment
score for those documents.

The "P-score" is a sum of the follower projection and aggregate sentiment score.


## Steps to run

Run the code sample without specifying any arguments by doing:
`./run.sh`

Run the code sample by specifying the optional arguments by doing:
`./run.sh --term "bars" --location "San Francisco, CA"`

## Steps to run on Windows

Run the code sample without specifying any arguments by doing:
`.\run.bat`

Run the code sample by specifying the optional arguments by doing:
`.\run.bat --term "bars" --location "San Francisco, CA"`
