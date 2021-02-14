//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Name         : Gump Unit | Module
// Purpose      : Offering Functions to work with Gumps
// Version      : 0.1 Alpha
// Requirements : Stealth 4.0.3 or Greater
// Author       : Crome696 aka Jan Florian Siems
// Last Change  : 04\28\2012 | 20:22 PM - Germany
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// I only provide this Content for Users of Scriptuo.com.
// If you aren´t a full Member of this Website i won´t offer any support.
// I don´t give any warranty of Loss of Items or Accounts.
// With using this Module the User accept my Terms.
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Scriptuo 
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Unit Gumps;
Interface
// Return Amount of Gumps
function CountGumps():Integer; 
// Returns Serial of Toplayer Gump
function GetLastSerial():Cardinal; 
// Returns GumpID of Toplayer Gump
function GetLastGumpID():Cardinal;
// Returns GumpInfo of Toplayer Gump 
function GetLastGump():TGumpInfo; 
// Wait until GumpID is Toplayer GumpID
procedure WaitGumpID(GumpID:Cardinal); 
// Wait until Serial is not Toplayer Serial
procedure WaitSerial(Serial:Cardinal); 
// Click on Button and maybe wait until Serial Changed and GumpID reached
procedure ClickButton(ButtonValue:Integer;GumpID:Cardinal;UseWait:Boolean); 
// Open Gump by Clicking on Object and maybe wait until Serial Changed and GumpID reached 
procedure ClickObject(ObjectID:Integer;GumpID:Cardinal;UseWait:Boolean);
// Clicks on Button left of Cliloc
procedure ClickClilocButton(Cliloc:Integer;RangeX,RangeY:Integer;GumpID:Cardinal;UseWait:Boolean);
// Simple Close a Gump
procedure CloseGump(Simple:Boolean);
// Press Button by TileArt
procedure ClickTileButton(TileArt:Integer;GumpID:Cardinal;UseWait:Boolean);
// Search for ClilocText 
function SearchClilocText(Cliloc:Integer):Boolean;
Implementation
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function CountGumps():Integer;
begin
  Result := (GetGumpsCount() - 1);
end;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function GetLastSerial():Cardinal;
begin
  Result := GetGumpSerial(CountGumps());  
end;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function GetLastGumpID():Cardinal;
begin
  Result := GetGumpID(CountGumps());  
end;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function GetLastGump():TGumpInfo;
begin
  GetGumpInfo(CountGumps(),Result);  
end;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
procedure WaitGumpID(GumpID:Cardinal);
begin
  while GetLastGumpID() <> GumpID do
  begin
    Wait(1);
  end; 
end;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
procedure WaitSerial(Serial:Cardinal);
begin
  while GetLastSerial() = Serial do
  begin
    Wait(1);
  end;
end; 
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
procedure ClickButton(ButtonValue:Integer;GumpID:Cardinal;UseWait:Boolean);
var Serial :Cardinal;
begin
  Serial := GetLastSerial();
  WaitGump(IntToStr(ButtonValue));
  if (UseWait = true) then
  begin
    WaitSerial(Serial);
    WaitGumpID(GumpID)
  end;
end;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
procedure ClickObject(ObjectID:Integer;GumpID:Cardinal;UseWait:Boolean);
var Serial :Cardinal;
begin
  Serial := GetLastSerial();
  UseObject(ObjectID);
  if (UseWait = true) then
  begin
    WaitSerial(Serial);
    WaitGumpID(GumpID)
  end;
end;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
procedure ClickClilocButton(Cliloc:Integer;RangeX,RangeY:Integer;GumpID:Cardinal;UseWait:Boolean);
var x:TGumpInfo;
var i,ii:Integer;
begin
  x :=GetLastGump();
  for i := 0 to (Length(x.XmfHTMLGumpColor) - 1)do
  begin
    if (x.XmfHTMLGumpColor[i].Cliloc_ID = Cliloc) then
    begin
      for ii := 0 to (Length(x.GumpButtons) - 1)do
      begin
        if 
        ((x.GumpButtons[ii].X = (x.XmfHTMLGumpColor[i].X - RangeX))and(x.GumpButtons[ii].Y = (x.XmfHTMLGumpColor[i].Y - RangeY)))then
        begin
          ClickButton(x.GumpButtons[ii].return_value,GumpID,UseWait);
          exit;
        end;
      end;  
    end;
  end; 
end;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
procedure CloseGump(Simple:Boolean);
var Serial :Cardinal;
begin
  Serial := GetLastSerial();
  if (Simple = true) then
  begin
    CloseSimpleGump(CountGumps());
  end else begin
    ClickButton(0,0,false);
  end;
  WaitSerial(Serial);  
end;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
procedure ClickTileButton(TileArt:Integer;GumpID:Cardinal;UseWait:Boolean);
var x : TGumpInfo;
var i : Integer;
var Serial :Cardinal;
begin
  Serial := GetLastSerial();
  x :=GetLastGump();
  for i := 0 to (Length(x.GumpButtons) - 1)do
  begin
    if ((x.GumpButtons[i].released_id = TileArt) or (x.GumpButtons[i].pressed_id = TileArt))then
    begin
      ClickButton(x.GumpButtons[i].return_value,GumpID,UseWait);
      exit;
    end; 
  end;
end;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function SearchClilocText(Cliloc:Integer):Boolean;
var x : TGumpInfo;
var i : Integer;
begin
  Result := false;
  x :=GetLastGump();
  for i := 0 to (Length(x.XmfHTMLGumpColor) - 1)do
  begin
    if (x.XmfHTMLGumpColor[i].Cliloc_ID = Cliloc) then
    begin
      Result := true; 
      exit;
    end;
  end; 
end;
end.