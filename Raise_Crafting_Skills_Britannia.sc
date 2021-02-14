Program Raise_Crafting_Skills_Britannia;

var

Const
Skill = '';
TailorT =  ;
BsT =       ;
CarpT =      ;
TinkerT = $1EB8;
AlcheT = ;
InscripT =;

IngotsType = $1BF2; 
LogType = $1BDD; 
WaitLag = 200;

function RoT_Gains(SkillCheck:Word):TDateTime ;
var
    cTime:TDateTime;

const
    T1:= 600;
    T2:= 1200;
    T3:= 1800;
    T4:= 2400;
    T5:= 3000;

begin
   If GetSkillValue(Skill) < 70 then Exit;
   If GetSkillValue(Skill) > 70 and GetSkillValue(Skill) < 80 then Result:=T1; 
   If GetSkillValue(Skill) >= 80 and GetSkillValue(Skill) < 90 then Result:=T2;
   If GetSkillValue(Skill) >= 90 and GetSkillValue(Skill) < 100 then Result:=T3; 
   If GetSkillValue(Skill) >= 100 and GetSkillValue(Skill) < 110 then Result:=T4;
   If GetSkillValue(Skill) > 110 and GetSkillValue(Skill) < 120 then Result:=T5;
end;

Function SelectCraft(Skill:String):Integer;
Begin
    If GetSkillValue(Skill) > 25 and GetSkillValue(Skill) < 35 then Result:= ;
   If GetSkillValue(Skill) > 70 and GetSkillValue(Skill) < 80 then Result:=; 
   If GetSkillValue(Skill) >= 80 and GetSkillValue(Skill) < 90 then Result:=;
   If GetSkillValue(Skill) >= 90 and GetSkillValue(Skill) < 100 then Result:=; 
   If GetSkillValue(Skill) >= 100 and GetSkillValue(Skill) < 110 then Result:=;
   If GetSkillValue(Skill) > 110 and GetSkillValue(Skill) < 120 then Result:=;
End;



procedure Craft(Tool:Word,Craft1,Craft2:Integer)
var 
Gumpy:Cardinal;

begin
    Findtype(Tool,Backpack);
    UseObject(Finditem);
    Wait(WaitLag);  
    NumGumpButton(GetGumpsCount-1,Craft1);
    Wait(750);
    NumGumpButton(GetGumpsCount-1,Craft2);
    Wait(750); 
                             
end;
procedure CraftLast(Tool:Word)
var 
Gumpy:Cardinal;

begin
    Findtype(Tool,Backpack);
    UseObject(Finditem);
    Wait(WaitLag);  
    NumGumpButton(GetGumpsCount-1,Craft1);
    Wait(750);  
                             
end;


begin
While(true) do begin
   if Dead or not Connected then Connect;
   CheckResources;
   Craft;
   end;
end.