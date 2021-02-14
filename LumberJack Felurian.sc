program CrazyLumber; 
{ 
Autor: Half-Life (a couple of functions and procedures, and in general a basis - were honestly removed from the Mining SA script by Fenix); 
Description: Script for tree felling. Recovered from manners or magic and hacks. The script was written with the expectation that the character is dressed in 100% lrk. And what would all work is necessary so that in the chest in the house were Protect scrolls. Before unloading the logs, chop them into boards with an ax. Runabooks with runes to the trees enter almost at the very bottom opposite the corresponding comment. 
UOStealthClientVersion: 6.2.1;
Warning! Be carefull! - The administration of many game servers is hostile to the use of stealth client on their servers. Suspecting you of using the client’s stealth and other non-Orthodox programs, they begin to satan and in a fit of blind rage they can fall on you with Banhammer; 
} 
const 
GumpIgnore = 0; 

IngotsStorage = $40195C01; // Chest in the house in which to throw the chopped. 

HomeRuneBook = $40100758; // Runabuki ID srnukoy in the house. 
HomeRuneIndex = 0; // position of the runes to the house in runabuka. 0 - the first fleece. 
RuneBookShift = 50; // 7 - chiva, 5 magic. 
HomeX = 932; // Coordinate X in front of the chest in the house. 
HomeY = 513; // Coordinate in front of the chest in the house. 

Arm = 62; // Amount of physical armor without protect.
Protection = $1F3B; // Scroll type protect. 

Axe = $4006D073; // ID of the ax you are hacking. 
LogType = $1BDD; // Type of logs. 

WaitTime = 500; 
RecalTime = 2000; 
LagWait = 10000; 

var
CurrentRune,CurrentBook:Integer;
Resourses,Killers:array of Word;
RuneBooks:array of Cardinal;
cTime,cTime2:TDateTime;

procedure Heal;
var
StartTime:TDateTime;
begin
  StartTime:=Now;
  if Poisoned then begin
    Cast('Cleanse by Fire');
    WaitForTarget(2000);
    TargetToObject(self);
  end;
  if (HP<>MaxHP) and (not Poisoned) then begin
    cast('Close Wounds');
    WaitForTarget(2000);
    TargetToObject(self);
  end;
  if (InJournalBetweenTimes('Your concentration is disturbed, thus ruining thy spell', StartTime, Now) > 0) or Poisoned or (HP<>MaxHP) then Heal;
  CancelWaitTarget;
  ClearJournal;
  Wait(500);
end;

procedure Move(Item:Array of Word);
var
j:Byte;
begin
  while FindType(LogType,Backpack)>0 do begin
    if Dead or not Connected then Exit;
    UseObject(Axe);
    CheckLag(LagWait);
    WaitForTarget(LagWait);
    TargetToObject(FindItem);
  end;
  CheckLag(LagWait);
  for j:=0 to Length(Item)-1 do begin
    if Dead or not Connected then Exit;
    CheckLag(LagWait);
    While (FindType(Item[j], Backpack)>1) do begin
      if Dead or not Connected then Exit;
      MoveItem(Finditem,GetQuantity(Finditem),IngotsStorage,0,0,0);
      CheckLag(LagWait);
      Wait(WaitTime);
    end;
  end;
end;

function RecallRune(RuneBook: Cardinal; Rune: Byte):Boolean;
var
X,Y:Word;
begin
  if Dead or not Connected then Exit;
  AddToSystemJournal('Runebook # '+IntToStr(CurrentBook+1));
  AddToSystemJournal('Rune # '+IntToStr(CurrentRune+1));
  while Weight > MaxWeight do begin
    FindType(LogType,Backpack);
    UseObject(Axe);
    CheckLag(LagWait);
    WaitForTarget(LagWait);
    TargetToObject(FindItem);
    RecallRune(HomeRuneBook, HomeRuneIndex)
    Move(Resourses);
  end;
  While IsGump do CloseSimpleGump(GetGumpsCount-1);
  Result:=False;
  X:=GetX(Self);
  Y:=GetY(Self);
  CheckLag(LagWait);
  Wait(WaitTime);
  if Dead or not Connected then Exit;
  cTime2:=Now;
  while (cTime2 < cTime)do begin
    cTime2:=Now;
    wait(100);
  end;
  UseObject(RuneBook);
  CheckLag(LagWait);
  cTime:=Now+0.00008;
  if IsGump then begin
    if NumGumpButton(GetGumpsCount-1, RuneBookShift + Rune) then begin
      CheckLag(LagWait);
      Wait(RecalTime);
      CheckLag(LagWait);
      Result := (X <> GetX(Self)) or (Y <> GetY(Self));
    end else Result:=False;
  end else Result:=False;
end;

function GoBase: Boolean;
begin
  if (GetX(self)=HomeX) and (GetY(self)=HomeY) then Exit;
  Result:=RecallRune(HomeRuneBook, HomeRuneIndex);
end;

function NextRune: Boolean;
begin
  if Dead or not Connected then Exit;
  if CurrentRune > 15 then begin
    CurrentRune := 0
    Inc(CurrentBook);
    if CurrentBook >= Length(RuneBooks) then CurrentBook := 0;
  end;
  While True do begin
    if Dead or not Connected then Exit;
    Result := RecallRune(RuneBooks[CurrentBook], CurrentRune);
    if Result then Break;
    Result := RecallRune(RuneBooks[CurrentBook], CurrentRune);
    if Result then Break;
    GoBase;
    Wait(10000);
  end;
end;

procedure CheckState(X,Y:Integer);
begin
  if Dead or not Connected then Exit;
  if MaxWeight < Weight + 70 then begin
    while True do begin
      if Dead or not Connected then Exit;
      if GoBase then Break;
      if not RecallRune(RuneBooks[CurrentBook], CurrentRune) then Wait(10000);
    end;
    Move(Resourses);
    while True do begin
      if Dead or not Connected then Exit;
      if RecallRune(RuneBooks[CurrentBook], CurrentRune) then Break;
    end;
  end;
  NewMoveXY(X,Y,True,1,True);
end;

function CheckPK: boolean;
var
i,q:integer;
begin
  FindDistance:=25;
  for q:=0 to high(Killers) do
  for i:=3 to 6 do
  if FindNotoriety(Killers[q],i)>0 then begin
    Result:=True;
    AddToSystemJournal('?????? ?????? ???? - ' + GetName(FindItem));
    AddToSystemJournal('Runebook # '+IntToStr(CurrentBook+1));
    AddToSystemJournal('Rune # '+IntToStr(CurrentRune+1));
    FindDistance:=2;
    Exit;
  end;
  FindDistance:=2;
  if (Poisoned) or (HP<>MaxHP) then Result:=True;
end;

procedure Mine(Tile,X,Y,Z:Integer);
var
StartTime:TDateTime;
begin
  while True do begin
    while ObjAtLayer(LhandLayer) = 0 do begin
      Equip(LhandLayer,Axe);
      wait(1000);
    end;
    if Dead or not Connected then Exit;
    if TargetPresent then CancelTarget;
    CheckState(x,y);
    CheckLag(LagWait);
    Wait(WaitTime);
    UseObject(Axe);
    CheckLag(LagWait);
    WaitForTarget(LagWait);
    if TargetPresent then begin
      StartTime := Now;
      TargetToTile(Tile, X, Y, Z);
      CheckLag(LagWait);
      if InJournalBetweenTimes('t use an axe |is too far away|cannot be seen|s not enough wood here to harvest', StartTime, Now) > 0 then Exit;
      Wait(200);
      CheckState(x,y);
      if CheckPK then begin
        cTime:=Now-0.00008;
        GoBase;
        Inc(CurrentRune);
        Heal;
        Wait(WaitTime*100);
        RecallRune(RuneBooks[CurrentBook], CurrentRune);
        CheckLag(LagWait);
        Wait(WaitTime);
      end;
    end;
  end;
end;

function CheckTiles:Array of array of Integer;
var
X0,Y0,i,q,x,y,One,Two:Integer;
StaticData:TStaticCell;
h:Byte;
TSTData:TStaticTileData;
Tiles:Array of array of Integer;
begin
  for i:=0 to 33 do begin
    x:=-15;
    y:=2;
    if i>0 then begin
      if One=Two then begin
        Inc(One);
      end
      else begin
        Inc(Two);
      end;
    end;
    While True do begin
      X0:=GetX(Self)+x+One;
      Y0:=GetY(Self)+y+Two;
      StaticData:=ReadStaticsXY(X0,Y0,WorldNum);
      if GetLayerCount(X0,Y0,WorldNum)<1 then begin
        if x>2 then break;
        Inc(x);
        Dec(y);
        Continue;
      end;
      TSTData:=GetStaticTileData(StaticData.Statics[0].Tile);
      h:=TSTData.Height;
      if (StaticData.Statics[0].Tile>3275) and (StaticData.Statics[0].Tile<3300) then begin
        SetLength(Tiles, q+1);
        SetLength(Tiles[q], 4);
        Tiles[q][0]:=StaticData.Statics[0].Tile;
        Tiles[q][1]:=StaticData.Statics[0].X;
        Tiles[q][2]:=StaticData.Statics[0].Y;
        Tiles[q][3]:=StaticData.Statics[0].Z;
        if x>2 then break;
        Inc(q);
        Inc(x);
        Dec(y);
        Continue;
      end
      else begin
        if x>2 then break;
        Inc(x);
        Dec(y);
        Continue;
      end;
    end;
  end;
  Result:=Tiles;
end;

procedure MinePoint;
var
i:Integer;
Tiles:Array of array of Integer;
begin
  if Dead or not Connected then Exit;
  Tiles:=CheckTiles;
  for i:=0 to Length(Tiles)-1 do begin
    NewMoveXY(Tiles[i][1],Tiles[i][2],True,1,True);
    Mine(Tiles[i][0],Tiles[i][1],Tiles[i][2],Tiles[i][3]);
  end;
  Inc(CurrentRune);
end;

procedure CheckProtect;
begin
  if Dead or not Connected then Exit;
  if Arm-15=Armor then Exit;
  GoBase;
  UseObject(IngotsStorage);
  CheckLag(LagWait);
  Wait(RecalTime);
  if CountEx(Protection,$FFFF,backpack)<=0 then begin
    if FindType(Protection,IngotsStorage)<=0 then begin
      AddToSystemJournal('??????????? ?????? ????????');
      Halt;
    end;
    repeat
      Grab(FindItem,1);
      CheckLag(LagWait);
      Wait(RecalTime);
    until CountEx(Protection,$FFFF,backpack)>0;
  end;
  repeat
    UseObject(FindItem);
    CheckLag(LagWait);
    Wait(RecalTime);
  until(Arm-15=Armor);
  CheckProtect;
end;

begin
  if not Connected then begin
    Connect;
    Wait(10000);
  end;
  While IsGump do CloseSimpleGump(GetGumpsCount-1);
  cTime:=Now;
  RuneBooks:= [$400646C5, $4007AE5D]; // Enter the runabuk ID with the runes to the trees here. 
  Resourses:= [$1BD7, $318F, $3191, $2F5F, $3199, $3190]; 
  Killers:=[$0190,$0191,$025E,$025D];
  //CheckProtect;
  while True do begin
    if Dead then begin
      AddToSystemJournal('You Dead.');
      AddToSystemJournal('Runebook # '+IntToStr(CurrentBook+1));
      AddToSystemJournal('Rune # '+IntToStr(CurrentRune+1));
      Halt;
    end;
    if not Connected then begin
      Connect;
      Wait(10000);
      While IsGump do CloseSimpleGump(GetGumpsCount-1);
      //CheckProtect;
      Continue;
    end;
    NextRune;
    MinePoint;
  end;
end.