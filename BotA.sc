Program BotA;
var
a:string;

Const
Player1:= ;
Player2:=  ;

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

Procedure Discordance(Monster:Cardinal);
var
ctime:TDateTime;

begin
UseSkill('Discordance');
ctime:=Now;
    if InJournalBetweenTimes('Which instrument shall you play?',ctime,Now)<>-1 then begin
        TargetToObject($aaaa);
    end else begin 
    TargetToObject(Monster);
    end;
end;
Procedure Ataque(Monster:Cardinal);
begin
    Attack(Monster);
end;

Procedure PartyA(Inviter:Cardinal);
begin
ClientPrint('/Accept');
end;

Procedure Tracking();
begin
    UseSkill('Tracking')

end;
Procedure Follow(Player:Cardinal);
begin
NewMoveXY(GetX(Player),GetY(Player),True,1,True);
end                         
Function CardiB(s:string):Cardinal
begin
Result:='$' + IntToHex(StrToInt(s),8);
end;



begin

 while True do begin
 a:=CardiB(GetGlobal('Stealth','Monster');
 Follow(Player1);
 SetEventProc(PartyInvite,'PartyA');
 Attack(a); 
 HpCheck;
 Discordance(a);
 //Tracking;
 end;
end.