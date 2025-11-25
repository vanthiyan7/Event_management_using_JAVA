import java.io.*;
import java.util.*;
interface IPerson
{
}

class Person implements IPerson
{
String firstName;
String lastName;

Person()
{
firstName =null;
lastName = null;
}

Person(String firstName , String lastName)
{
this.firstName = firstName;
this.lastName = lastName;
}

public String toString()
{
return firstName+""+lastName;
}
}

class EDate
{
int date;
String month;
int year;

EDate(int date,String month ,int year)
{
this.date = date;
this.month = month;
this.year = year;
}

int getYear()
{
return year;
}
}

interface IEventInfo
{
abstract void register(IPerson p);
abstract void attend(IPerson p);
}


class EventInfo implements IEventInfo
{
String eventName;
EDate eventDate;
int capacity;
boolean canceled;
List<IPerson> registration =new ArrayList<>();;
List<IPerson> attendees = new ArrayList<>(); ;

EventInfo()
{
eventName =null;
eventDate =null;
capacity = 0;
canceled =false; 
}

EventInfo(String eventName , EDate eventDate , int capacity , boolean canceled)
{
this.eventName =eventName;
this.eventDate = eventDate;
this.capacity = capacity;
this.canceled = canceled;
}

public void register(IPerson p)
{
boolean registered = false;
for(IPerson k : registration)
if(k.firstName.equalsIgnoreCase(p.firstName) && k.lastName.equalsIgnoreCase(p.lastName))
{
registered = true;
break;
}

if(canceled==false && capacity > registration.size() && registered == false)
{
registration.add(p);
System.out.println(p+" is registered successfully");
}

else if(registration.size()>=capacity)
System.out.println("Registration closed");

else if(registered == true)
System.out.println(p+" is already registered");
}

public void attend(IPerson p)
{
System.out.println(registration);
if(canceled==false && registration.contains(p)==false && attendees.contains(p)==false)
{
attendees.add(p);
System.out.println(p+" is added to the attendees list");
}
else if(canceled==true)
System.out.println("Event is cancelled");

else if(registration.contains(p)==true)
System.out.println(p+" is not registered");

else if(attendees.contains(p)==true)
System.out.println(p+" is already in the attendees list");
}

public String toString()
{
return eventName;
}
}

interface IEventManager
{
abstract void addEvent(IEventInfo e);
abstract void register(String eventName,IPerson p);
abstract void attend(String eventName,IPerson p);
abstract List<String> getEventCountByYears();
abstract List<String> getEventRegistrationCountByYears();
abstract List<String> getEventAttendeesCountByYears();
}

class EventManager implements IEventManager
{
List<EventInfo> events;

EventManager()
{
events = new ArrayList<>();
}

public void addEvent(IEventInfo e)
{
EventInfo ei=(EventInfo)e;
if(events.size()==0)
{
events.add(ei);
System.out.println(ei+"is added");
}
else
{
int f=0;
for(EventInfo k:events)
if(k.eventName.equals(ei.eventName))
{
f=1;
break;
}
if(f==0)
{
events.add(ei);
System.out.println(ei+"is added");
}
else
System.out.println("Already present in the events list");
}
}


public void register(String eventName , IPerson p)
{
int f=0;
for(EventInfo k:events)
{
if(k.eventName.equals(eventName))
{
k.register(p);
f =1;
break;
}
if(f == 0)
System.out.println("Event not exist");
}
}


public void attend(String eventName , IPerson p)
{
int f=0;
for(EventInfo k:events)
{
if(k.eventName.equals(eventName))
{
k.attend(p);
f =1;
break;
}
}
if(f == 0)
System.out.println("Event not exist");

}


public List<String> getEventCountByYears()
{
Map<Integer,Integer> map=new LinkedHashMap<>();
events.sort((a,b) -> Integer.compare(a.eventDate.getYear(),b.eventDate.getYear()));
for(EventInfo k:events)
map.merge(k.eventDate.getYear(),1,Integer::sum);
List<String> list=new ArrayList<>();
for(Map.Entry<Integer,Integer> e : map.entrySet())
list.add(e.getKey()+" - "+e.getValue());
return list;
}

public List<String> getEventRegistrationCountByYears()
{
Map<Integer,Integer> map=new LinkedHashMap<>();
events.sort((a,b) -> Integer.compare(a.eventDate.getYear(),b.eventDate.getYear()));
for(EventInfo k:events)
map.merge(k.eventDate.getYear(),k.registration.size(),Integer::sum);
List<String> list=new ArrayList<>();
for(Map.Entry<Integer,Integer> e : map.entrySet())
list.add(e.getKey()+" - "+e.getValue());
return list;
}


public List<String> getEventAttendeesCountByYears()
{
Map<Integer,Integer> map=new LinkedHashMap<>();
events.sort((a,b) -> Integer.compare(a.eventDate.getYear(),b.eventDate.getYear()));
for(EventInfo k:events)
map.merge(k.eventDate.getYear(),k.attendees.size(),Integer::sum);
List<String> list=new ArrayList<>();
for(Map.Entry<Integer,Integer> e : map.entrySet())
list.add(e.getKey()+" - "+e.getValue());
return list;
}
}

class EventManagerDriver
{
public static void main(String arg[])throws IOException
{
EventManager em=new EventManager();
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
int ch;

do
{
System.out.println("Event Registration----->1");
System.out.println("To register event---->2");
System.out.println("Attend event----->3");
System.out.println("Get event count----->4");
System.out.println("Get event registration count---->5");
System.out.println("Get event attendees count--->6");
System.out.println("Exit---->7");
System.out.println("Enter ur choice:");
ch=Integer.parseInt(br.readLine());

switch(ch)
{
case 1:
System.out.println("Enter event name,date,month,year of the event & capacity");
String name=br.readLine();
int date =Integer.parseInt(br.readLine());
String month =br.readLine();
int year = Integer.parseInt(br.readLine());
EDate d=new EDate(date,month,year);
int c=Integer.parseInt(br.readLine());
EventInfo e=new EventInfo(name,d,c,false);
em.addEvent(e);
break;

case 2:
System.out.println("Enter ur First name and last name");
String firstName = br.readLine();
String lastName = br.readLine();
IPerson  p=new Person(firstName,lastName);
System.out.println("Enter which event u want to attend");
String eventName = br.readLine();
em.register(eventName,p);
break;

case 3:
System.out.println("Enter ur First name and last name");
String firstNm = br.readLine();
String lastNm = br.readLine();
IPerson  pp=new Person(firstNm,lastNm);
System.out.println("Enter which event u want to register");
String eventNm = br.readLine();
em.attend(eventNm,pp);
break;

case 4:
System.out.println("Event Count");
System.out.println(em.getEventCountByYears());
break;

case 5:
List<String> list=em.getEventRegistrationCountByYears();
System.out.println("Registrations");
System.out.println(list);
break;

case 6:
System.out.println("Attendees");
System.out.println(em.getEventAttendeesCountByYears());
break;

case 7:
break;

default:
System.out.println("Invalid choice");
}
}while(ch!=7);

}
}

