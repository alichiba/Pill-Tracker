# Pill Tracker Project

---

## Introduction
Created by Alison Hardy

This application is primarily designed to be a program that can be used to 
track the number of medications taken over the course of a week. 
It can also be repurposed to keep track of other elements such as
water, or exercise despite the fact that the primary design and intention is for
medications. As such, anyone could make use the application, but it will likely be most useful
to people who have medications that they have to take on a regular basis, whether that
be seniors who choose to take vitamin supplements, women on birth control or even 
people who frequently have to take pain-killers. Some aspects of code were sourced from the 
demos provided by the CPSC210 course offered at UBC.


This project is of interest to me because I think that many people could
benefit from having a digitalized way to keep track of pills or any other regular activity which 
encourages actively and continuously building a routine. Personally, keeping 
track and reminding myself to take vitamin D supplements is something I forget to do
even to this day. As such, I feel that having a simple and convenient application
to log behaviour would help people form healthy habits or improve on unhealthy ones.
This project was worked on as a component of the CPSC210 course at UBC. After completing the
course I revisited the project to fully complete the project and improve the design
and code implementation for some aspects. It is now complete although there are areas for
improvement, like always, some of which I have noted below the user stories.

### User Stories:
- As a user I want to be able to **add** a pill to the history of medications taken in a day
- As a user I want to be able to **remove** an individual pill from the history of medications
- As a user I want to be able to **see** a summary of the number of pills for this week, last week, and the target
- As a user I want to be able to **see** a visualization of the pills taken each day in a week
- As a user I want to be able to manually **move** to the next week (not realized in Graphical UI)
- As a user I want to be able to **set** a target amount for a week

- As a user, I want to be able to **save** my current tracker state to file
- As a user, I want to be able to **load** my saved tracker state from file 

### Further Improvements
Looking at the UML diagram alone, there are not too many excessive associations.
- More thorough test cases for pills that may have the same name, or is intended to refer to the same object including user mistypes. Potential alteration to upper vs lowercase letters in names.
- Better UI for Graphical app, particularly for the select day, add, and remove containers. Image for meeting set target could also be updated.
- Storing hashmap in Json file without using JSONArray and reconstructing pills by name (necessary if pill class is modified to contain more information than just a name. Potentially could have multiple JSONArrays)
- User story of moving to the next week is lost in the Graphical Tracker App. This would improve the UX of the Graphical App.
- Potentially more complex data could be stored in the Pill class beyond a name. This would require changes to the display and storage of data.

---
### Sample Series of Events stored in Event Log:  
Added to Sunday: pill1 on Wed Mar 30 11:45:54 PDT 2022  
Added to Sunday: pill2 on Wed Mar 30 11:45:57 PDT 2022  
Added to Sunday: pill3 on Wed Mar 30 11:45:58 PDT 2022  
Removed from Sunday: pill3 on Wed Mar 30 11:46:07 PDT 2022  
Added to Sunday: pill4 on Wed Mar 30 11:46:10 PDT 2022  
Added to Sunday: pill5 on Wed Mar 30 11:46:17 PDT 2022  
Removed from Sunday: pill5 on Wed Mar 30 11:46:21 PDT 2022  
