# Project 1: Virustotal automate scanner

Our project is used for scanning files (<650mb), URLs, domain names, IP addresses through Virustotal's APIs. This project is a part of the "Project 1" course of Hanoi University of Science and Technology.

## Table of Contents

- [Usage](#usage)
- [Features](#features)

## Usage

To start the application, you have to run /src/main/main.java. The application will ask for an API key of Virustotal (read https://support.virustotal.com/hc/en-us/articles/115002088769-Please-give-me-an-API-key for information about getting an API key):
![image](https://github.com/ndmch3w/Project1Test1/assets/130122471/3472c632-dce6-4f03-8b18-4736e8fcc964)

After input a proper API key, the option menu appears, you need to input your choice to scan your object:
![image](https://github.com/ndmch3w/Project1Test1/assets/130122471/69ac37c6-6d34-46ab-9bfd-37487d29b097)

Next, type in the filepath (if you're scanning file), URL (if you're scanning URL), domain name (if you're scanning domain), IP address ((if you're scanning IP).

If the object exists, the reports will be stored in the form of .json, .txt, .csv, .pdf (recommended to see the summarized results, including a visualized bar chart, in /results_PDF).


## Features

- Scan files: Scan for results come from different antivirus engines, community reputation score, file types, hashed values, trid, ...
- Scan URLS: Scan for results come from different antivirus engines, community reputation score, redirection chain, content length, ...
- Scan domains: Scan for results come from different antivirus engines, community reputation score, whois information, ...
- Scan IPs: Scan for results come from different antivirus engines, community reputation score, whois information, country, ...

