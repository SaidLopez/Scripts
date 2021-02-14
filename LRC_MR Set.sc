Program Imbuing_Lrc_Suit;
var
EquipI:array of word;
EquipC:array of integer;

const
Sewing = ;
ArmsG =   ;
LegsG =    ;
HeadG =     ;
ChestG =     ;
GlovesG =     ;
SleevesG =     ;
Arms =          ;
Legs =          ;
Head  =           ;
Chest =            ;
Gloves =            ;
Sleeves =            ;
Imb_LRC = ;
Imb_MR = ;
BagID = ;


procedure Armor(I:Integer);
begin
  if findtype(Sewing,Backpack)<>-1 then useobject(Finditem);
  wait(200);
  NumGumpButton(GetGumpsCount-1,I);
  CloseSimpleGump(GetGumpsCount-1);
end;
Procedure Imbue(I:Cardinal,Prop:Integer);
Begin
   Useskill('Imbuing');
   NumGumpButton(GetGumpsCount-1,Prop);
   TargetToObject(I);
   wait(200);
   CloseSimpleGump(GetGumpsCount-1);
end;

Procedure Bagit(I:Cardinal);
begin
FindType(BagID,Backpack);
  MoveItem(I,1,FindItem,0,0,0);
end;

                        
begin

  EquipC = [ArmsG,LegsG,HeadG,ChestG,GlovesG,SleevesG]; 
    for i:=0 to Length(EquipC-1) do begin
  Armor(EquipC[i]);
  end;
  wait(125);
  EquipI = [Arms,Legs,Head,Chest,Gloves,Sleeves];
  for i:=0 to Length(Equip-1) do begin
  Findtype(Equip[i],Backpack);
  Imbue(Finditem,Imb_LRC);
  Imbue(Finditem,Imb_MR);
  Bagit(Finditem);
  end;
end.