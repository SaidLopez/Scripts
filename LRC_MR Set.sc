Program Imbuing_Lrc_Suit;
var
EquipI:array of word;
EquipC:array of integer;
i:integer;

const
Sewing = $0F9D ;
NeckG = 23;
LegsG = 51   ;
HeadG = 30    ;
ChestG = 58    ;
GlovesG = 37    ;
SleevesG = 44    ;
Neck =  $13C7        ;
Legs =   $13CB       ;
Head  =  $1DB9         ;
Chest =  $13CC          ;
Gloves =  $13C6          ;
Sleeves =  $13CD          ;
BagID = $0E75;


procedure Armor(I:Integer);
begin
  if findtype(Sewing,Backpack)<>-1 then useobject(Finditem);
  wait(200);
  NumGumpButton(GetGumpsCount-1,I);
  wait(1500);
  if Isgump then CloseSimpleGump(GetGumpsCount-1);
end;
Procedure ImbueLRC(I:Cardinal);
Begin
   Useskill('Imbuing');
   wait(1000);
   NumGumpButton(GetGumpsCount-1,10005);
   waitfortarget(1000);
   TargetToObject(I);
   wait(400);
   NumGumpButton(GetGumpsCount-1,10118);
   wait(1000);  
   NumGumpButton(GetGumpsCount-1,10056); 
   wait(1000);  
   NumGumpButton(GetGumpsCount-1,10051);   
   wait(1000);  
   NumGumpButton(GetGumpsCount-1,10051);
   wait(1000);  
   NumGumpButton(GetGumpsCount-1,10100);
   wait(1000)
   CloseSimpleGump(GetGumpsCount-1);
end;
Procedure ImbueMR(I:Cardinal);
Begin
   Useskill('Imbuing');
   wait(1000);
   NumGumpButton(GetGumpsCount-1,10005);
   waitfortarget(1000);
   TargetToObject(I);
   wait(400);
   NumGumpButton(GetGumpsCount-1,10005);
   wait(1000); 
   NumGumpButton(GetGumpsCount-1,10105); 
   wait(1000);  
   NumGumpButton(GetGumpsCount-1,10100);
   wait(1000);   
   CloseSimpleGump(GetGumpsCount-1);
end;

Procedure Bagit(I:Cardinal);
begin
FindType(BagID,Backpack);
  MoveItem(I,1,FindItem,0,0,0);
end;

                        
begin

  EquipC := [NeckG,LegsG,HeadG,ChestG,GlovesG,SleevesG]; 
    for i:=0 to Length(EquipC)-1 do begin
  Armor(EquipC[i]);
  wait(500);
  end;
  wait(125);
  EquipI := [Neck,Legs,Head,Chest,Gloves,Sleeves];
  for i:=0 to Length(EquipI)-1 do begin
  Findtype(EquipI[i],Backpack);
  ImbueLRC(Finditem);
  wait(1000);
  ImbueMR(Finditem);
  Bagit(Finditem);
  end;
end.