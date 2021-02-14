Program Animal_Taming; //v1.3h 

var 
ctime: TDateTime; 
animal_type: array [1..2] of Byte; 
i: Integer; 

procedure Kill(ID:Cardinal);
begin
Attack(ID);
 while WarTargetID=ID do begin
  NewMoveXY(GetX(ID),GetY(ID),False,1,True);
  wait(1000);
 end ;
ClearJournal;
end;

procedure Tame(Animal:Cardinal);
begin
if TargetPresent then CancelTarget; 
      NewMoveXY(GetX(Animal),GetY(Animal),True,1,True)
      UseSkill ('Animal Taming');       
      WaitTargetObject (Animal);
        if (InJournal('tame already.')<>-1) then begin
        Kill(Animal);
       end else begin     
      repeat
      NewMoveXY(GetX(Animal),GetY(Animal),True,1,True)
      wait(1000);
      until InJournalBetweenTimes (('You fail to tame the creature.| even challenging'), ctime, Now)<>-1; 
      end;
        if InJournal('You fail to tame the creature.')<>-1  then begin
            Tame(Animal);
        end else if InJournal('accept you as master.')<>-1 then begin
            Kill(Animal); 
            end;
end;

BEGIN 
animal_type [1]:= $00E8; // bull
animal_type [2]:= $00EA;// great hart 


i:= 1; 



while (Dead = False) do 
begin 
    FindDistance := 20;
    
   if (i> 0) and (i <= 2) then 
   begin
   while FindTypeEX (animal_type[i], $FFFF, Ground, False) <> 0 do 
      begin 
      Tame(FindItem); 
      end; 
   end; 
end; 
End.