Program Bot_Lead;

var
LeadEnemy:Cardinal;

Const
Bandage = $FFFF;
Player1 = $;
Player2 = $;

procedure Lead_Enemy(Enemy:Cardinal);  
begin
SetGlobal('Stealth','EN',inttostr(Enemy));
end;

procedure Heal(Player:Cardinal);
var
ctime:TDateTime;
begin
FindTypeEX(Bandage,Backpack,$FFFF,0,0,0);
    if GetQuantity(Finditem) < 10 then begin
        AddToSystemJournal('Only' + IntToStr(GetQuantity(Finditem)) 'left!!!' );
    end;
UseObject(Finditem);
TargetToObject(Player);
ctime:=Now;
    repeat
    NewMoveXY(GetX(Player),GetY(Player),True,1,True);
    until InJournalBetweenTimes('You finish applying the bandages',ctime,now)<>-1;
end;



Procedure HpCheck();
begin
If HP(Char) > 85 then begin
    If HP(Player1) < HP(Player2) and HP(Player1) < 85 then
        Heal(Player1);
    If HP(Player1) > HP(Player2) and HP(Player2) < 85 then   
        Heal(Player2);
    end else begin
        Heal(Self);  
    end;      
end;

begin

    while True do begin
       Lead_Enemy(WarTargetID);
       HpCheck;    
    end;
end.