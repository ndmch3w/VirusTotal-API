# Project 1: Virustotal automated scanner

Our project is used for scanning files (<650mb), URLs, domain names, IP addresses through Virustotal's APIs. This project is a part of the "Project 1" course of Hanoi University of Science and Technology.

## Table of Contents

- [Usage](#usage)
- [Features](#features)

## Usage

To start the application, you have to run /src/main/main.java. The application will ask for an API key of Virustotal (read https://support.virustotal.com/hc/en-us/articles/115002088769-Please-give-me-an-API-key for information about getting an API key):

![image](https://github.com/ndmch3w/Project1Test1/assets/130122471/3472c632-dce6-4f03-8b18-4736e8fcc964)

After inputting a proper API key, the options menu appears, you need to input your choice to scan your object:

![image](https://github.com/ndmch3w/Project1Test1/assets/130122471/69ac37c6-6d34-46ab-9bfd-37487d29b097)

Next, type in the filepath (if you're scanning file), URL (if you're scanning URL), domain name (if you're scanning domain), IP address ((if you're scanning IP).
For instances (the example objects below can be malicious):
 + URL: https://www.powerfinanceworld.com, https://credltagricole-contact.46-30-203-97.plesk.page/13209, https://diepostl.com/
 + Domain: clicnews.com, tryteens.com
 + IP: 139.198.38.106, 144.24.197.112

If the object exists, the reports will be stored in the form of .json, .txt, .csv, .pdf (recommended to see the summarized results, including a visualized bar chart, in /results_PDF).


## Features

- Scan files: Scan for results from different engines, community reputation score, file types, hashed values, trid, ...
- Scan URLS: Scan for results from different engines, community reputation score, redirection chain, content length, ...
- Scan domains: Scan for results from different engines, community reputation score, whois information, ...
- Scan IPs: Scan for results from different antivirus engines, community reputation score, whois information, country, ...

