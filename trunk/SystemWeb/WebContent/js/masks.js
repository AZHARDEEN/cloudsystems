function applyMask(compId, mask)
{
    compId = '#' + compId;
    jQuery(compId).mask(mask);
}



function applyZipCodeMask(compId)
{
   applyMask(compId, "99.999-999")
}

function applyPhoneMask(compId)
{
   applyMask(compId, "(99) 9999-9999");
}


function applyCPFMask(compId)
{
   applyMask(compId, "999.999.999-99");
}


function applyCNPJMask(compId)
{
   applyMask(compId, "99.999.999/9999-99");
}


