# EasyJournal

## 5-minute summaries of cool events in life

I've realized recently that I can be quite forgetful about day-to-day events. 
One solution to this would, of course, be to start writing in a journal or a
blog, but I've always found myself not really knowing how to start with a
journal. That's why I came up with **EasyJournal**.

**EasyJournal** streamlines the journaling process. Users can commemorate events
with just a few short categorizing selections, answers to pre-written 
questions, and a couple of images. Each event will be recorded under a specific
day. The user will then be able to go into a calendar view and click into events 
logged on any day. They will also be able to see statistics about some of their 
answers for all logged events. Finally, they will be able to generate a 
slideshow of all events from start to finish, as well as a slideshow of events 
that fit under a certain filter.

I envision this application to be for people who want to be able to remember 
aspects of their lives but are too lazy or busy to write full on journal 
entries every day.

## User Stories
As a user, I want to be able to ...
- create a new event and record it in my journal under a specific day
- view a list of all events recorded under a specific day
- categorize events by different tags that themselves record how often they are
used
- view statistics about my logged events at the aggregate level 
- view a list of all events recorded under a certain tag
- enter a calendar view in which I can click into any day and see the events 
recorded under that day
- view a slideshow of all recorded events
- select an event from the past and add an "in hindsight" comment
- when I select the quit option from the application menu, I want to be 
prompted to save my recorded journal to file and have the *option* to do so or 
not
- when I start the application, I want to be given the *option* to load my 
previously recorded journal from file

# Instructions for End User

- You can generate the first required action related to the user story "adding 
multiple Xs to a Y" by clicking into the "My Days" tab, clicking on the date 
associated with existing day to enter that day (or add a day first), and then
clicking on the "Add Event" button at the top of the screen.
- You can generate the second required action related to the user story "adding 
multiple Xs to a Y" by clicking into the "My Days" tab, clicking on the date 
associated with existing day to enter that day (or add a day first), and then
scrolling to the bottom and clicking "show average rating" to show the average
rating of that day.
- You can locate my visual component by clicking into the "My Days" tab, 
clicking on the date associated with existing day to enter that day (or add a 
day first), viewing existing events that do have images, or adding an event to
a day with your own image (stored in the project folder) and viewing the event 
under the day.
- You can save the state of my application by going to the menu bar at the top,
clicking "Options", and then selecting the "Save" menu item.
- You can reload the state of my application by going to the menu bar at the 
top, clicking "Options", and then selecting the "Load" menu item.

# Phase 4: Task 2
Here is a representative sample of the events that occur when my program runs:
- Day added to journal!
- Day added to journal!
- Event added to day!
- Event's star status flipped from false to true!
- Tag added to event!
- Tag added to event!
- Event added to day!
- Event's star status flipped from false to true!
- Event's star status flipped from true to false!
- Tag added to event!
- Tag added to event!

# Phase 4: Task 3
In the future, I would love to refactor many aspects of my UI. Specifically, 
I'd refactor my big console UI class to be a few smaller classes, each with
their own responsibilities. I would also refactor the DaysTab class in my GUI
to separate the two entirely different pages that are drawn onto the same tab.

Additionally, I would refactor some of the methods that I have to eliminate
duplicate code. For instance, right now, in my DaysTab class, I have many 
methods that create a button, and these buttons are essentially initialized in
the same way except for their text. I would love to refactor this code so that
I have a single method that creates this type of button, taking parameters when
necessary.