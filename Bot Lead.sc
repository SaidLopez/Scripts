Program Bot_Lead;

var
a,b:Integer;

Const
Bandage = $0E21;
Player1 = $00008109;
Player2 = $000062CB;


procedure Heal(Player:Cardinal);
var
ctime:TDateTime;
begin
FindType(Bandage,Backpack)
    if GetQuantity(Finditem) < 10 then begin
        AddToSystemJournal('Only' + IntToStr(FindQuantity)+ 'left!!!');
    end;
UseObject(Finditem);
WaitForTarget(2000);
TargetToObject(Player);
WaitJournalLine(Now,'You finish applying the bandages.',15000);
end;

Procedure HpCheck(a,b:integer);
begin
If GetHP(Self()) > 65 then begin 
     If (a < 85) and (a < b) then begin
        Heal(Player1); 
     end;
      If (a > b) and (b < 85) then begin  
        Heal(Player2);
     end
end else begin
     Heal(Self);   
     end;
end;      

begin

    while True do begin 
            if WarTargetID <> - 1 then Begin
            SetGlobal('Stealth','Lead',IntToStr(WarTargetID));
            end;
       a:=GetHP(Player1);
       //AddToSystemJournal('Billie Tiene ' + IntToStr(GetHP(Self)))  
       //AddToSystemJournal('Feluria Tiene ' + IntToStr(GetHP(Player1)))
       b:=GetHP(Player2);  
       HpCheck(a,b); 
       wait(200);   
    end;
end.