Program Carpentry;

/// by Igor Boldysh.
          var

  saw, tinkertool, ingot, wood: Integer;
 Trash, boxMaterial, item: Cardinal;
first, second: string;

 
procedure Init;   


 Begin 
 
  SetPauseScriptOnDisconnectStatus (true);
    Finddistance: = 30;   



    Trash: = $ 404AD727;
    boxMaterial: = $ 41485E64;
    Wood: = $ 1BD7;
    saw: = $ 1034;
    tinkertool: = $ 1eb8;
    ingot: = $ 1bf2;
    item: = $ 0; 


  end;   

procedure checkSkill;
 // 0.0-30.0: By from NPC Carpenter 8/51 Carpentry
 //30.0-45.0: Wooden Box 15/2
 //45.0-48.0: Trinsic Style Chair 8/37
 //48.0-65.0: Ballot Box 43/128
 //65.0-78.0: Fukiya 22/30
 //78.0-95.0: Bokuto 22/23
 //95.0-100.0: Sheppard Crook 22/37
 
begin
if ((GetSkillValue ('Carpentry ')> = 30) and (GetSkillValue (' Carpentry ') <45)) then
begin
first: =' 15 ';
second: = '2';
item: = $ 09AA; //30.0-45.0: Wooden Box 15/2
exit;
end;
if ((GetSkillValue ('Carpentry')> = 45) and (GetSkillValue ('Carpentry') <48)) then
begin
first: = '8';
second: = '37';
item: = $ 0B53; //45.0-48.0: Trinsic Style Chair 8/37
exit;
end;
if ((GetSkillValue ('Carpentry')> = 48) and (GetSkillValue ('

first: = '43';
second: = '128';
item: = $ 14F0; //48.0-65.0: Ballot Box 43/128
exit;
end;
if ((GetSkillValue ('Carpentry')> = 65) and (GetSkillValue ('Carpentry') <78)) then
begin
first: = '22';
second: = '30';
item: = $ 27AA; //65.0-78.0: Fukiya 22/30
exit;
end;
if ((GetSkillValue ('Carpentry')> = 78) and (GetSkillValue ('Carpentry') <95)) then
begin
first: = '22';
second: = '23';
item: = $ 27A8; //78.0-95.0: Bokuto 22/23
exit;
end;
if ((GetSkillValue ('Carpentry')> = 95) and (GetSkillValue ('Carpentry') <
100)) then begin
first: = '22';
second: = '2';         
item: = $ 0E81;
exit //95.0-100.0: Sheppard Crook 22/37
end;
end;

 
   procedure check;
 begin
if (count (wood) <20) then
begin
UseObject (boxMaterial);
wait (500);
grab (FindTypeEx (wood, $ 0000, boxMaterial, true), 100);
wait (1000)
end;
if (count (ingot) <20) then
begin
UseObject (boxMaterial);
wait (500);
grab (FindTypeEx (ingot, $ 0000, boxMaterial, true), 100);
wait (1000)
end;
 end;
   
    procedure craftTools (typetool, typeitem: Integer; first, second: String);
    begin 
     
  if (count (typeitem) <2) then
  begin
  while (count (typeitem) <5) do
  begin   
 check;   

    UseObject (FindType (typetool, backpack)); 
     UseObject (backpack);
WaitGump (first);
wait (500);
WaitGump (second);
  end;
  end;                         
    end;
     
 
   
 procedure craft (tool: word);
 begin 
checkSkill;
if (FindType (tool, backpack) <> 0) then
begin
  UseObject (FindType (tool, backpack));
  wait (500);

WaitGump (first);
wait (200);
WaitGump (second);
wait (1000);
end
else UseObject (backpack);
check;
 end;
 
 procedure trashItems;
 begin
 if (CountEx (item, $ FFFF, backpack)> 0) then
begin
MoveItem (FindTypeEx (item, $ 0000, backpack, true), 1, trash, 0,0,0);
wait (500)
// AddToSystemJournal ('trash');
end;
 end;
 
Begin
init
UseObject (backpack);
wait (500);
UseObject (boxMaterial);
wait (500);
while (GetSkillValue ('Carpentry') <GetSkillCap ('Carpentry')) do
begin
 craft (saw);
  craftTools (tinkertool, tinkertool, '8', '23');
  craftTools (tinkertool, saw, '8', '51');
 trashItems;
 
end;
End.