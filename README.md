# Hotel management system
Team Members: Hongxiang Zheng, Xiang Cao, Yingbin Zheng.
Repository: http://github.com/AllenZHX/JavaProject
#Project Description:
We develop a Hotel Management System used JAVA combine with MySQL. We focus on booking, check in, service and check out.
Swing:  mainly including the knowledge of JLabel, JTextField, JButton, JTabel, JPanel, JTabbedPane, JComboBox, FlowLayout, GridLayout, JScrollPane and so on.
DataBase: We will use the MySQL database.(use JDBC to connect database with Java)

#Objective:
1.      booking: can show the roomstatus(booked or not) and choose the time to checkin and checkout when we want to book a room, allow other computers to share the database(Booking Client)
2.      Check in: Require NAME and ID and book a room(single room, double room or family size)
3.      Service: drink, food, massage, pickup(Car Server)
4.      Check out: Settle account.
5.      Bill: can show us the monthly bill includes num of check-in people, num of check-out people and total money.      

#The responsibility distribution:
1.     Hongxiang Zheng and Yingbin Zheng will be in charge of  use Swing to design the MyHotel system interface(6 parts:"booking" window, "check in" window, "order serve" window, "check out" window, "bill" window and "show table" window, in order to  landscaping interface, we will add a “current time” window and a “Logo” window)
2.     Hongxiang Zheng and Xiang cao will be responsible for  use JDBC to connect MySQL database(complete the operations like generate, delete, modify,save). We also need create some tables like service table, customers’ info table and so on.
3.     Yingbin Zheng will create a interface for customers, which can realize the booking part in another computer through database sharing function.
4.     Xiang Cao will use Stocket to create a car rental interface, which can receive the order info from hotel, then send a notice back to hotel.

#Main classes and structure:
just see the classStructure.vsd
https://www.draw.io/?state=%7B%22ids%22:%5B%220B6G2nMMHIC1rMWJ5b2ZpZHJjM3c%22%5D,%22action%22:%22open%22,%22userId%22:%22104859114805731306837%22%7D#G0B6G2nMMHIC1rMWJ5b2ZpZHJjM3c


