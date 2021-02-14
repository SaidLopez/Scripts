Program New;

//uses RoT_SkillGain;

const
  Forge = $40000351;
  IngotsStorage = $40010752;
  IngotsType = $1BF2;     
  SeekRange = 20;
  
  //HomeRuneBook = $400B600D;
  //HomeRuneIndex = 0;
  //RuneBookShift = 50; //50 for Recal, 75 for Sacred Journey, 100 for Gate Travel
  
  MiningType = $0F39;
  
 // TinkerType = $1EB8;
 // TKNumFirst = 15;
 // TKNumSecond = 21;
  
 // TKMinerNumFirst = 15;
 // TKMinerNumSecond = 72;
  
  IronColor = $0000;
  IronCount = $20;
  
  WaitTime = 500;
 // RecalTime = 5000;
  WaitCycles = 7;
  LagWait = 15000; 

type
MinTile = record
x, y, z, Tile : Word;
end;
  
var
  Terminated: Boolean;
 // CurrentRune: Byte;
 // CurrentBook: Integer;
  OreTypes: array of Word;
  GemTypes: array of Word;
  //RuneBooks: array of Cardinal;
 // MiningTool: Cardinal;
//  TinkerTool: Cardinal;  
MinTiles_Array : Array of MinTile;
i,MineResult:integer;
cSkill:double;
function RoT_Gains(Skill:String):Cardinal ;
   var
   T1,T2,T3,T4,T5:Cardinal;
Begin

    T1:= 600000;
    T2:= 1200000;
    T3:= 1800000;
    T4:= 2400000;
    T5:= 3000000;

   If GetSkillValue(Skill) < 70 then Exit;
   If (GetSkillValue(Skill) > 70) and (GetSkillValue(Skill) < 80) then Result:=T1; 
   If (GetSkillValue(Skill) >= 80) and (GetSkillValue(Skill) < 90) then Result:=T2;
   If (GetSkillValue(Skill) >= 90) and (GetSkillValue(Skill) < 100) then Result:=T3; 
   If (GetSkillValue(Skill) >= 100) and (GetSkillValue(Skill) < 110) then Result:=T4;
   If (GetSkillValue(Skill) > 110) and (GetSkillValue(Skill) < 120) then Result:=T5;      
   
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
procedure GoStart(); //New
begin
NewMoveXY(1416,1694,true,0,true);
wait(2000);
NewMoveXY(1448,1519,true,0,true);
  //Result := RecallRune(HomeRuneBook, HomeRuneIndex);
end;
 procedure GoBase(); //New
begin
NewMoveXY(1425,1555,true,0,true);
wait(1000);
SmellOre;
NewMoveXY(1422,1564,true,0,true);
wait(2000);
NewMoveXY(1427,1695,true,0,true);
UOsay('Bank');
MoveIngots;
//GoStart;
 // Result := RecallRune(HomeRuneBook, HomeRuneIndex);
end;


procedure CheckState;
begin
  if MaxWeight < Weight + 40 then begin
    //while True do begin
      if Dead or not Connected then Exit;
      GoBase;
      
      //if not RecallRune(RuneBooks[CurrentBook], CurrentRune) then Wait(10000);
   // end;
    
    //SmellOre;
   // MoveIngots;
    //MoveGems;
    
   // while True do begin
   //   if Dead or not Connected then Exit;
      //if RecallRune(RuneBooks[CurrentBook], CurrentRune) then Break;
      //if RecallRune(RuneBooks[CurrentBook], CurrentRune) then Break;
   //   GoStart;
      //if not NextRune then Wait(10000);
  //  end;
  end;
  //while not CheckMiningTool do begin
  //  if Dead or not Connected then Exit;
 //   CreateMiningTools;
 // end;
end;






procedure CheckTiles();
var
 x, y, i : Integer;
 TileInfo : TStaticCell;       
 MapInfo:TMapCell;
 
begin
    SetLength(MinTiles_Array, 0);
   
    for x := (-1 * SeekRange) to SeekRange do
        for y := (-1 * SeekRange) to SeekRange do
        begin  
            MapInfo:=GetMapCell(GetX(self)+x, GetY(self)+y, WorldNum);
           // TileInfo := ReadStaticsXY(GetX(self)+x, GetY(self)+y, WorldNum);
            if MapInfo.Tile > 0 then begin
               // for i := Low(TileInfo.Statics) to High(TileInfo.Statics) do
                    if (MapInfo.Tile >= 561) and (MapInfo.Tile <= 579){ and (TileInfo.Statics[i].z = GetZ(self))} then
                    begin
                        SetLength(MinTiles_Array, Length(MinTiles_Array) + 1);
                        MinTiles_Array[High(MinTiles_Array)].Tile := MapInfo.Tile;
                        MinTiles_Array[High(MinTiles_Array)].x := GetX(self)+x ;
                        MinTiles_Array[High(MinTiles_Array)].y := GetY(self)+y;
                        MinTiles_Array[High(MinTiles_Array)].z := GetMapCell(GetX(self)+x, GetY(self)+y, WorldNum).z;
                    end;                           
        end;
        end;
       
    AddToSystemJournal('Found ' + IntToStr(Length(MinTiles_Array)) + ' tiles for mining.');   
end;   


function MinTileSpot(Idx : Integer) : Integer;
var
 i, k : Integer;
 msgFizzle, msgEnd, msgUseless, msgAttack : String;
 cTime : TDateTime;
     
begin
    msgFizzle := 'You dig |You loosen |не получилось ';
    msgEnd := 'is nothing| too far| mining in rock| cannot be seen.| no line| reach| not to mine|Try mining|слишком далеко|прекратили';
    msgUseless := 'no metal|the Copper Ore|the Bronze Ore';
    msgAttack := 'is attacking you';
    result := 0;
    if Dist(GetX(self), GetY(self), MinTiles_Array[Idx].x, MinTiles_Array[Idx].y) > 1 then
        NewMoveXY(MinTiles_Array[Idx].x, MinTiles_Array[Idx].y, true, 1, true);
      
    for k := 0 to 4 do begin
        cSkill:=GetSkillValue('Mining');

       
        if (ObjAtLayerEx(RhandLayer,self) = 0) then begin
            if FindType(MiningType, Backpack) = 0 then begin
                AddToSystemJournal('There are no pickaxes in a backpack.');
                result := 1;
                exit;                                             
            end;     
        end;
           
        if WarMode then SetWarMode(False);           
        if UseType(MiningType, $FFFF) = 0 then
            UseType(MiningType, $FFFF);
           
        CheckLag(LagWait);           
        if not WaitForTarget(10000) then
        begin
            AddToSystemJournal('There is no target for a long time (probably we lose a pickaxe).');
            result := 1;
            exit;                           
        end
        else
        begin
            cTime := Now;
            TargetToXYZ(MinTiles_Array[Idx].x, MinTiles_Array[Idx].y, MinTiles_Array[Idx].z);                   
           
            for i := 0 to 100 do
            begin
                Wait(100);
                CheckLag(LagWait);
                if (InJournalBetweenTimes(msgAttack, cTime, Now) <> -1) then begin result := 2; exit; end;
                if (InJournalBetweenTimes(msgEnd, cTime, Now) <> -1) then exit;
                if (InJournalBetweenTimes(msgUseless, cTime, Now) <> -1) then exit;
                if (InJournalBetweenTimes(msgFizzle, cTime, Now) <> -1) then break; 
            end;
        end;
        CheckState;  
        if cSkill<GetSkillValue('Mining') then begin
        AddtoSystemJournal('Mining is: ' +FloatToStr(GetSkillValue('Mining')));
        Wait(RoT_Gains('Mining'));
        end;
    end;           
end;


begin
OreTypes := [$19B7, $19B8, $19B9, $19BA];
  GemTypes := [$0F2D, $0F15, $0F16, $0F25, $0F13, $0F21,$0F26,$0F0F,$3195,$0F11,$0F18,$3192,$0F10,$3193,$3197,$3194,$3198];

 // CurrentBook := 0;
 // CurrentRune := 0;
  
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
    GoStart;
    CheckTiles;
    for i := Low(MinTiles_Array) to High(MinTiles_Array) do begin
            MineResult := MinTileSpot(i);   
            if MineResult = 1 then break;
            end;
  end;
end.