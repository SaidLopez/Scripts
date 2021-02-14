program Mining;

const
  Forge = $4005DE7E;
  IngotsStorage = $4001D327;
  IngotsType = $1BF2;
  
  HomeRuneBook = $400B600D;
  HomeRuneIndex = 0;
  RuneBookShift = 50; //50 for Recal, 75 for Sacred Journey, 100 for Gate Travel
  
  MiningType = $0F39;
  
  TinkerType = $1EB8;
  TKNumFirst = 15;
  TKNumSecond = 23;
  
  TKMinerNumFirst = 15;
  TKMinerNumSecond = 72;
  
  IronColor = $0000;
  IronCount = $20;
  
  WaitTime = 500;
  RecalTime = 5000;
  WaitCycles = 7;
  LagWait = 15000;
  
var
  Terminated: Boolean;
  CurrentRune: Byte;
  CurrentBook: Integer;
  OreTypes: array of Word;
  GemTypes: array of Word;
  RuneBooks: array of Cardinal;
  MiningTool: Cardinal;
  TinkerTool: Cardinal;

procedure EventMinerGump(Serial, GumpID, X, Y: Cardinal);
begin
  if NumGumpButton(0, TKMinerNumSecond) then Exit;
  if NumGumpButton(0, TKMinerNumFirst) then Exit;
  while IsGump do CloseSimpleGump(0);
end;


procedure EventTinkerGump(Serial, GumpID, X, Y: Cardinal);
begin
  if NumGumpButton(0, TKNumSecond) then Exit;
  if NumGumpButton(0, TKNumFirst) then Exit;
  while IsGump do CloseSimpleGump(0);
end;

  
function CheckMiningTool: Boolean; //New
begin
  CheckLag(LagWait);
  FindType(MiningType, Backpack);
  if GetType(MiningTool) <> MiningType then MiningTool := FindItem;
  Result := FindCount > 0;
end;


function CheckTinkerTool: Boolean; //New
begin
  CheckLag(LagWait);
  FindType(TinkerType, Backpack);
  if GetType(TinkerTool) <> TinkerType then TinkerTool := FindItem;
  Result := FindCount > 1;
end;

procedure CreateTKTools;//New
var
  Counter: Cardinal;
begin
  AddToSystemJournal('Делаем тинкер тузлы');
  SetEventProc(evIncomingGump, 'EventTinkerGump');
  UseObject(TinkerTool);
  Counter := 0;
  while True do begin
    if (Dead)
      or (not Connected)
      or (CheckTinkerTool) then begin
      SetEventProc(evIncomingGump, '');
      Break;
    end else Wait(1000);
    Inc(Counter);
    if Counter > WaitCycles then begin
      SetEventProc(evIncomingGump, '');
      Break;
    end;
  end;
  while IsGump do CloseSimpleGump(0);
  AddToSystemJournal('Сделали тинкер тузлы');
end;

function CreateMiningTools: Boolean;//New
var
  Counter: Cardinal;
begin
//  AddToSystemJournal('Делаем минер тузлы');
  SetEventProc(evIncomingGump, 'EventMinerGump');
  if not CheckTinkerTool then begin
    CreateTKTools;
  end;
  UseObject(TinkerTool);
  Counter := 0;
  while True do begin
    if (Dead)
      or (not Connected)
      or (CheckMiningTool) then Break;
    Inc(Counter);
    if Counter > WaitCycles then Break;
    Wait(1000);
  end;
  SetEventProc(evIncomingGump, '');
  while IsGump do CloseSimpleGump(0);
  Result := CheckMiningTool;
//  AddToSystemJournal('Сделали минер тузлы');
end;

procedure SmellOre; //New
var
  CurOre, CurIndex: Integer;
  CurItem: Cardinal;
  List: TStringList;
begin
  try
    List := TStringList.Create;
    for CurOre := 0 to Length(OreTypes) - 1 do begin
      if Dead or not Connected then Exit;
      CheckLag(LagWait);
      FindType(OreTypes[CurOre], Backpack);
      List.Clear;
      if GetFindedList(List) then begin
        CurIndex := 0;
        while CurIndex < List.Count do begin
          if Dead or not Connected then Exit;
          CurItem := StrToInt('$' + List.Strings[CurIndex]);
          CheckLag(LagWait);
          if (GetType(CurItem) <> OreTypes[CurOre])
            or (GetQuantity(CurItem) < 2) then begin
            Inc(CurIndex);
          end else begin
            if TargetPresent then CancelTarget;
            UseObject(CurItem);
            CheckLag(LagWait);
            WaitForTarget(WaitTime * 5);
            if TargetPresent then begin
              TargetToObject(Forge);
              CheckLag(LagWait);
              Wait(WaitTime);
            end;
            CheckLag(LagWait);
          end;
        end;
      end;
    end;
  finally
    List.Free;
  end;
end;

procedure MoveIngots; //New
var
  List: TStringList;
  CurIndex: Integer;
  CurIngot: Cardinal;
  CurIron: Cardinal;
  StartCount, ToMove: Integer;
begin
  CheckLag(LagWait);
  FindType(IngotsType, BackPack);
  CurIron := 0;
  try
    List := TStringList.Create;
    if GetFindedList(List) then begin
      CurIndex := 0;
      while CurIndex < FindCount do begin
        if Dead or not Connected then Exit;
        CurIngot := StrToInt('$' + List.Strings[CurIndex]);
        CheckLag(LagWait);
        StartCount := GetQuantity(CurIngot);
        if (GetColor(CurIngot) = IronColor)
          and (CurIron < IronCount) then begin
          ToMove := StartCount - (IronCount - CurIron);
        end else begin
          ToMove := StartCount;
        end;
        if ToMove > 0 then begin
          if MoveItem(CurIngot, ToMove, IngotsStorage, $FFFF, $FFFF, 0) then begin
            Inc(CurIndex);
            CurIron := CurIron + (StartCount - ToMove);
            CheckLag(LagWait);
            Wait(WaitTime);
          end;
        end else begin
          Inc(CurIndex);
          CurIron := CurIron + StartCount;
        end;
      end;
    end;
  finally
    List.Free;
  end;
end;

procedure MoveGems; //New
var
  List: TStringList;
  CurGem, CurIndex: Integer;
  CurItem: Cardinal;
begin
  CheckLag(LagWait);
  try
    List := TStringList.Create;
    for CurGem := 0 to Length(GemTypes) - 1 do begin
      if Dead or not Connected then Exit;
      CheckLag(LagWait);
      FindType(GemTypes[CurGem], Backpack);
      List.Clear;
      if GetFindedList(List) then begin
        CurIndex := 0;
        while CurIndex < List.Count do begin
          if Dead or not Connected then Exit;
          CurItem := StrToInt('$' + List.Strings[CurIndex]);
          CheckLag(LagWait);
          if GetType(CurItem) <> GemTypes[CurGem] then begin
            Inc(CurIndex);
          end else begin
            Wait(WaitTime);
            MoveItem(CurItem, GetQuantity(CurItem), IngotsStorage, 0, 0, 0);
          end;
        end;
      end;
    end;
  finally
    List.Free;
  end;
end;


function RecallRune(RuneBook: Cardinal; Rune: Byte):Boolean; //New
var
  Counter: Byte;
  X, Y: Word;
begin
  Result := False;
  X := GetX(Self);
  Y := GetY(Self);
  CheckLag(LagWait);
  Wait(WaitTime);
  while Isgump do CloseSimpleGump(0);
  if Dead or not Connected then Exit;
  UseObject(RuneBook);
  CheckLag(LagWait);
  Counter := WaitCycles;
  while Counter > 0 do begin
    if IsGump then Break;
    Wait(WaitTime);
    CheckLag(LagWait);
    Inc(Counter);
  end;
  if IsGump then begin
    if NumGumpButton(0, RuneBookShift + Rune) then begin
      CheckLag(LagWait);
      Wait(RecalTime);
      CheckLag(LagWait);
      Result := (X <> GetX(Self)) or (Y <> GetY(Self));
    end else Result := False;
  end else Result := False;
end;


function GoBase: Boolean; //New
begin
  Result := RecallRune(HomeRuneBook, HomeRuneIndex);
end;


function NextRune: Boolean; //New
var
  Counter: Cardinal;
begin
  Inc(CurrentRune);
  if CurrentRune > 15 then begin
    CurrentRune := 0
    Inc(CurrentBook);
    if CurrentBook >= Length(RuneBooks) then CurrentBook := 0;
  end;
  for Counter := 0 to WaitCycles do begin
    if Dead or not Connected then Exit;
    Result := RecallRune(RuneBooks[CurrentBook], CurrentRune);
    if Result then Break;
    Result := RecallRune(RuneBooks[CurrentBook], CurrentRune);
    if Result then Break;
    GoBase;
    Wait(10000);
  end;
end;


procedure CheckState;
begin
  if MaxWeight < Weight + 60 then begin
    while True do begin
      if Dead or not Connected then Exit;
      if GoBase() then Break;
      if GoBase() then Break;
      if not RecallRune(RuneBooks[CurrentBook], CurrentRune) then Wait(10000);
    end;
    
    SmellOre;
    MoveIngots;
    MoveGems;
    
    while True do begin
      if Dead or not Connected then Exit;
      if RecallRune(RuneBooks[CurrentBook], CurrentRune) then Break;
      if RecallRune(RuneBooks[CurrentBook], CurrentRune) then Break;
      if GoBase() then Continue;
      if not NextRune then Wait(10000);
    end;
  end;
  while not CheckMiningTool do begin
    if Dead or not Connected then Exit;
    CreateMiningTools;
  end;
end;


procedure Mine(X, Y: Integer);
var
  StaticData: TStaticCell;
  Tile: Word;
  Z: ShortInt;
  Finded: Boolean;
  Counter: Byte;
  StartTime: TDateTime;
  i: Integer;
begin
  Finded := False;
  StaticData := ReadStaticsXY(X, Y, WorldNum);
  for i := 0 to StaticData.StaticCount - 1 do begin
    if i >= StaticData.StaticCount then Break;
    if (GetTileFlags(2, StaticData.Statics[i].Tile) and $200) = $200 then begin
      Tile := StaticData.Statics[i].Tile;
      Z := StaticData.Statics[i].Z;
      Finded := True;
      Break;
    end; 
  end;
  
  CheckState(); 
  while Finded do begin
    if Dead or not Connected then Exit;
    if TargetPresent then CancelTarget;
    while not CheckMiningTool do begin
      if Dead or not Connected then Exit;
      CreateMiningTools;
    end;
    CheckLag(LagWait);
    Wait(WaitTime);
    UseObject(MiningTool);
    CheckLag(LagWait);
    WaitForTarget(WaitTime);
    if TargetPresent then begin
      StartTime := Now;
      TargetToTile(Tile, X, Y, Z);
      Counter := WaitCycles;
      Finded := False;
      while (not Finded) and (Counter > 0) do begin
        CheckLag(LagWait);
        if InJournalBetweenTimes('t mine there|is too far away|cannot be seen|is no metal here to mine', StartTime, Now) > 0 then Exit;
        if InJournalBetweenTimes('put it in your backpack|loosen some rocks but fail to find any useable ore|have worn out your tool', StartTime, Now) > 0 then Finded := True;
        if Not Finded then Wait(200);
        Dec(Counter);
      end;
      CheckState();
    end;
  end;    
end;

procedure MinePoint; //New
var
  X, Y: Word;
begin
  X := GetX(Self);
  Y := GetY(Self);
  Mine(X, Y);
  Mine(X + 1, Y);
  Mine(X + 1, Y + 1);
  Mine(X, Y + 1);
  Mine(X - 1, Y + 1);
  Mine(X - 1, Y);
  Mine(X - 1, Y - 1);
  Mine(X, Y - 1);
  Mine(X + 1, Y - 1);
  Mine(X + 2, Y + 2);
  Mine(X - 2, Y + 2);
  Mine(X - 2, Y - 2);
  Mine(X + 2, Y - 2);
end;

begin

  RuneBooks := [$400C041C,$400B6166];
  OreTypes := [$19B7, $19B8, $19B9, $19BA];
  GemTypes := [$0F2D, $0F15, $0F16, $0F25, $0F13, $0F21,$0F26,$0F0F,$3195,$0F11,$0F18,$3192,$0F10,$3193,$3197,$3194,$3198];

  CurrentBook := 0;
  CurrentRune := 0;
  
  SetEventProc(evIncomingGump, '');
  
  while not Terminated do begin
    if Dead then begin
      Terminated := True;
      Continue;
    end;
    if not Connected() then begin
      Connect();
      Wait(10000);
      Continue;
    end;
    NextRune;
    MinePoint;
  end;
end.