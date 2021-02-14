Program GlobalHP;

SetGlobal('Stealth','HP1',IntToStr(Life));


Function GHP(s:string):Integer;
var

begin
    Result:=StrToInt(s);
end;


begin 

 AddToSystemJournal('Life is ' + IntToStr(GHP(GetGlobal('Stealth','HP1')));
 
end.