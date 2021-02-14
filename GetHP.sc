Program GlobalHP;
begin


Function GHP(s:string):Integer;
begin
    Result:=StrToInt(s);
end;


begin 
 SetGlobal('Stealth','HP1',IntToStr(Life));
 AddToSystemJournal('Life is ' + IntToStr(GHP(GetGlobal('Stealth','HP1')));
       end;
end.