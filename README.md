# JavaProject
Hotel management system
Team Members: Xiang Cao, 
              Hongxiang Zheng,
		      Yingbin Zheng.
Create Repository on Github: http://github.com/****************

Proposal
1.	Check in(require NAME and ID) and book a room(single, double or family size)(consider how long they wanna stay, and notice when the time is close. It sounds like that we need a standard time. I am not sure it is difficult or not. We’ll see it later)
2.	Serve: drink, food, dry cleaning, massage
3.	Settle account and check out
How to do:
1.	Use Swing to design the system interface(4 part: “check in” window, “booking” window, “order serve” window and “pay money” window)
2.	Use JDBC to connect MySQL database(generate, delete, modify)(actually I find out that we can just write some easy function in the MySQL, called “procedure”, right?)
3.	We need create some database like food and drink list, room list and so on. (give it more details)

{Main classes:
Run(); // run the system
PopUp(); // pop up a notice window(like “Successfully check in!”, “Room # is almost closingtime”, “money is not enough”, “run out of some food”, “Successfully checkout!”)
CreateANewUser(Name, ID); // check in
AddBookingInfo(roomsize, time); // store the booking information(showing empty rooms in the interface on real-time would be great.)
AddOrderItem(itemname, num, price); // record the order stuff
SettleAccount(); // sum all the price together
CalculateChange(); // according paid-up money to calculate how much is the change
……
               }
