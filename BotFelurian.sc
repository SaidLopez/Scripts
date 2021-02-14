Program BotA;
var
a,b:Integer;
c:Cardinal;

Const
Bandage = $0E21;
Player1= $000092AA; //Billie
Player2= $000062CB; //Tryneowy
HuntID= $FE5C638B;
Lute = $0EB3;
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
ctime:=Now;
    repeat
    NewMoveXY(GetX(Player),GetY(Player),True,1,True);
    until InJournalBetweenTimes('You finish applying the bandages.',ctime,now)<>-1;
end;



Procedure HpCheck(a,b:integer);
begin
If GetHP(Self) > 65 then begin 
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

Procedure Discordance(Monster:Cardinal);
var
ctime:TDateTime;

begin
UseSkill('Discordance');
ctime:=Now;
    if InJournalBetweenTimes('Which instrument shall you play?',ctime,Now)<>-1 then begin
        FindType(Lute,Backpack);
        waitforTarget(2000);
        TargetToObject(Finditem);
    end;
    WaitforTarget(2000); 
    TargetToObject(Monster);
    end;
Procedure Ataque(Monster:Cardinal);
begin
    Attack(Monster);
end;

Procedure PartyA();
begin
ClientPrint('/Accept');
end;

Procedure Hunt();
begin
    UseSkill('Tracking');
    wait(1000);
    if GetGumpID(GetGumpsCount-1)=HuntID then begin 
    NumGumpButton(GetGumpsCount-1,6); 
end;
end;
Procedure Follow(Player:Cardinal);
begin
NewMoveXY(GetX(Player),GetY(Player),True,1,True);
end;                         
Function Bicho(s:string):Cardinal;
var
h: int64;
begin
h:=StrToInt64(s);
result:=h;
end;


begin
     FindDistance:=20;
     FindVertical:=5; 
     If GetSkillValue('Tracking') > 1 then begin 
       Hunt; 
     end;
 while True do begin
    //SetEventProc(evSpeech,'PartyA');
    Follow(Player1);
    c:=Bicho(GetGlobal('Stealth','Lead'));
        If WarTargetID <> c then  Ataque(c);  
       a:=GetHP(Player1);
       b:=GetHP(Player2);     
       HpCheck(a,b);
       if GetSkillValue('Discordance') > 30 then begin
        if WarTargetID <> 0 then Discordance(c);
       end; 
       wait(200);   

    end;
 
 end.