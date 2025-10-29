GO

/****** Object:  UserDefinedFunction [dbo].[HTMLDecode]    Script Date: 6/28/2021 9:40:29 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

IF object_id('HTMLDecode', 'FN') IS NOT NULL
BEGIN
    DROP FUNCTION [dbo].[HTMLDecode]
END
GO

CREATE FUNCTION [dbo].[HTMLDecode] (@text NVARCHAR(4000))
RETURNS NVARCHAR(4000) AS
BEGIN
	DECLARE @vcResult NVARCHAR(4000)
	DECLARE @vcCrLf NVARCHAR(2)
	DECLARE @siPos SMALLINT
	DECLARE @vcEncoded NVARCHAR(7)
	DECLARE @siChar SMALLINT

	SET @vcCrLF = CHAR(13) + CHAR(10)

	SELECT @vcResult = @text
	SELECT @siPos = PATINDEX('%&#___;%', @vcResult)
	WHILE (@siPos > 0)
	BEGIN
		SELECT @vcEncoded = SUBSTRING(@vcResult, @siPos, 6)
		SELECT @siChar = CAST(SUBSTRING(@vcEncoded, 3, 3) AS SMALLINT)
		SELECT @vcResult = REPLACE(@vcResult, @vcEncoded, NCHAR(@siChar))
		SELECT @siPos = PATINDEX('%&#___;%', @vcResult)
	END

	SELECT @siPos = PATINDEX('%&#____;%', @vcResult)
	WHILE (@siPos > 0)
	BEGIN
		SELECT @vcEncoded = SUBSTRING(@vcResult, @siPos, 7)
		SELECT @siChar = CAST(SUBSTRING(@vcEncoded, 3, 4) AS SMALLINT)
		SELECT @vcResult = REPLACE(@vcResult, @vcEncoded, NCHAR(@siChar))
		SELECT @siPos = PATINDEX('%&#____;%', @vcResult)
	END

	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&quot;', NCHAR(0x0022))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&amp;', NCHAR(0x0026))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '<', NCHAR(0x003c))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '>', NCHAR(0x003e))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&nbsp;', NCHAR(0x00a0))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&iexcl;', NCHAR(0x00a1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&cent;', NCHAR(0x00a2))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&pound;', NCHAR(0x00a3))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&curren;', NCHAR(0x00a4))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&yen;', NCHAR(0x00a5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&brvbar;', NCHAR(0x00a6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sect;', NCHAR(0x00a7))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&uml;', NCHAR(0x00a8))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&copy;', NCHAR(0x00a9))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ordf;', NCHAR(0x00aa))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&laquo;', NCHAR(0x00ab))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&not;', NCHAR(0x00ac))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&shy;', NCHAR(0x00ad))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&reg;', NCHAR(0x00ae))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&macr;', NCHAR(0x00af))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&deg;', NCHAR(0x00b0))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&plusmn;', NCHAR(0x00b1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sup2;', NCHAR(0x00b2))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sup3;', NCHAR(0x00b3))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&acute;', NCHAR(0x00b4))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&micro;', NCHAR(0x00b5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&para;', NCHAR(0x00b6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&middot;', NCHAR(0x00b7))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&cedil;', NCHAR(0x00b8))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sup1;', NCHAR(0x00b9))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ordm;', NCHAR(0x00ba))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&raquo;', NCHAR(0x00bb))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&frac14;', NCHAR(0x00bc))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&frac12;', NCHAR(0x00bd))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&frac34;', NCHAR(0x00be))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&iquest;', NCHAR(0x00bf))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Agrave;', NCHAR(0x00c0))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Aacute;', NCHAR(0x00c1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Acirc;', NCHAR(0x00c2))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Atilde;', NCHAR(0x00c3))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Auml;', NCHAR(0x00c4))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Aring;', NCHAR(0x00c5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&AElig;', NCHAR(0x00c6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Ccedil;', NCHAR(0x00c7))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Egrave;', NCHAR(0x00c8))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Eacute;', NCHAR(0x00c9))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Ecirc;', NCHAR(0x00ca))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Euml;', NCHAR(0x00cb))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Igrave;', NCHAR(0x00cc))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Iacute;', NCHAR(0x00cd))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Icirc;', NCHAR(0x00ce ))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Iuml;', NCHAR(0x00cf))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ETH;', NCHAR(0x00d0))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Ntilde;', NCHAR(0x00d1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Ograve;', NCHAR(0x00d2))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Oacute;', NCHAR(0x00d3))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Ocirc;', NCHAR(0x00d4))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Otilde;', NCHAR(0x00d5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Ouml;', NCHAR(0x00d6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&times;', NCHAR(0x00d7))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Oslash;', NCHAR(0x00d8))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Ugrave;', NCHAR(0x00d9))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Uacute;', NCHAR(0x00da))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Ucirc;', NCHAR(0x00db))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Uuml;', NCHAR(0x00dc))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Yacute;', NCHAR(0x00dd))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&THORN;', NCHAR(0x00de))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&szlig;', NCHAR(0x00df))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&agrave;', NCHAR(0x00e0))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&aacute;', NCHAR(0x00e1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&acirc;', NCHAR(0x00e2))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&atilde;', NCHAR(0x00e3))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&auml;', NCHAR(0x00e4))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&aring;', NCHAR(0x00e5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&aelig;', NCHAR(0x00e6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ccedil;', NCHAR(0x00e7))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&egrave;', NCHAR(0x00e8))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&eacute;', NCHAR(0x00e9))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ecirc;', NCHAR(0x00ea))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&euml;', NCHAR(0x00eb))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&igrave;', NCHAR(0x00ec))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&iacute;', NCHAR(0x00ed))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&icirc;', NCHAR(0x00ee))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&iuml;', NCHAR(0x00ef))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&eth;', NCHAR(0x00f0))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ntilde;', NCHAR(0x00f1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ograve;', NCHAR(0x00f2))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&oacute;', NCHAR(0x00f3))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ocirc;', NCHAR(0x00f4))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&otilde;', NCHAR(0x00f5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ouml;', NCHAR(0x00f6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&divide;', NCHAR(0x00f7))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&oslash;', NCHAR(0x00f8))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ugrave;', NCHAR(0x00f9))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&uacute;', NCHAR(0x00fa))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ucirc;', NCHAR(0x00fb))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&uuml;', NCHAR(0x00fc))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&yacute;', NCHAR(0x00fd))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&thorn;', NCHAR(0x00fe))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&yuml;', NCHAR(0x00ff))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&OElig;', NCHAR(0x0152))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&oelig;', NCHAR(0x0153))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Scaron;', NCHAR(0x0160))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&scaron;', NCHAR(0x0161))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Yuml;', NCHAR(0x0178))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&fnof;', NCHAR(0x0192))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&circ;', NCHAR(0x02c6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&tilde;', NCHAR(0x02dc))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Alpha;', NCHAR(0x0391))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Beta;', NCHAR(0x0392))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Gamma;', NCHAR(0x0393))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Delta;', NCHAR(0x0394))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Epsilon;', NCHAR(0x0395))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Zeta;', NCHAR(0x0396))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Eta;', NCHAR(0x0397))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Theta;', NCHAR(0x0398))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Iota;', NCHAR(0x0399))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Kappa;', NCHAR(0x039a))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Lambda;', NCHAR(0x039b))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Mu;', NCHAR(0x039c))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Nu;', NCHAR(0x039d))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Xi;', NCHAR(0x039e))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Omicron;', NCHAR(0x039f))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Pi;', NCHAR(0x03a0))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '& Rho ;', NCHAR(0x03a1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Sigma;', NCHAR(0x03a3))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Tau;', NCHAR(0x03a4))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Upsilon;', NCHAR(0x03a5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Phi;', NCHAR(0x03a6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Chi;', NCHAR(0x03a7))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Psi;', NCHAR(0x03a8))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Omega;', NCHAR(0x03a9))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&alpha;', NCHAR(0x03b1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&beta;', NCHAR(0x03b2))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&gamma;', NCHAR(0x03b3))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&delta;', NCHAR(0x03b4))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&epsilon;', NCHAR(0x03b5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&zeta;', NCHAR(0x03b6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&eta;', NCHAR(0x03b7))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&theta;', NCHAR(0x03b8))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&iota;', NCHAR(0x03b9))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&kappa;', NCHAR(0x03ba))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&lambda;', NCHAR(0x03bb))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&mu;', NCHAR(0x03bc))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&nu;', NCHAR(0x03bd))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&xi;', NCHAR(0x03be))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&omicron;', NCHAR(0x03bf))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&pi;', NCHAR(0x03c0))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&rho;', NCHAR(0x03c1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sigmaf;', NCHAR(0x03c2))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sigma;', NCHAR(0x03c3))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&tau;', NCHAR(0x03c4))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&upsilon;', NCHAR(0x03c5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&phi;', NCHAR(0x03c6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&chi;', NCHAR(0x03c7))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&psi;', NCHAR(0x03c8))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&omega;', NCHAR(0x03c9))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&thetasym;', NCHAR(0x03d1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&upsih;', NCHAR(0x03d2))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&piv;', NCHAR(0x03d6))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ensp;', NCHAR(0x2002))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&emsp;', NCHAR(0x2003))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&thinsp;', NCHAR(0x2009))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&zwnj;', NCHAR(0x200c))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&zwj;', NCHAR(0x200d))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&lrm;', NCHAR(0x200e))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&rlm;', NCHAR(0x200f))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ndash;', NCHAR(0x2013))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&mdash;', NCHAR(0x2014))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&lsquo;', NCHAR(0x2018))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&rsquo;', NCHAR(0x2019))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sbquo;', NCHAR(0x201a))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ldquo;', NCHAR(0x201c))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&rdquo;', NCHAR(0x201d))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&bdquo;', NCHAR(0x201e))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&dagger;', NCHAR(0x2020))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Dagger;', NCHAR(0x2021))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&bull;', NCHAR(0x2022))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&hellip;', NCHAR(0x2026))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&permil;', NCHAR(0x2030))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&prime;', NCHAR(0x2032))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&Prime;', NCHAR(0x2033))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&lsaquo;', NCHAR(0x2039))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&rsaquo;', NCHAR(0x203a))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&oline;', NCHAR(0x203e))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&frasl;', NCHAR(0x2044))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&euro;', NCHAR(0x20ac))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&image;', NCHAR(0x2111))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&weierp;', NCHAR(0x2118))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&real;', NCHAR(0x211c))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&trade;', NCHAR(0x2122))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&alefsym;', NCHAR(0x2135))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&larr;', NCHAR(0x2190))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&uarr;', NCHAR(0x2191))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&rarr;', NCHAR(0x2192))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&darr;', NCHAR(0x2193))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&harr;', NCHAR(0x2194))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&crarr;', NCHAR(0x21b5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&lArr;', NCHAR(0x21d0))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&uArr;', NCHAR(0x21d1))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&rArr;', NCHAR(0x21d2))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&dArr;', NCHAR(0x21d3))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&hArr;', NCHAR(0x21d4))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&forall;', NCHAR(0x2200))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&part;', NCHAR(0x2202))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&exist;', NCHAR(0x2203))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&empty;', NCHAR(0x2205))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&nabla;', NCHAR(0x2207))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&isin;', NCHAR(0x2208))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&notin;', NCHAR(0x2209))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ni;', NCHAR(0x220b))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&prod;', NCHAR(0x220f))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sum;', NCHAR(0x2211))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&minus;', NCHAR(0x2212))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&lowast;', NCHAR(0x2217))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&radic;', NCHAR(0x221a))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&prop;', NCHAR(0x221d))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&infin;', NCHAR(0x221e))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ang;', NCHAR(0x2220))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&and;', NCHAR(0x2227))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&or;', NCHAR(0x2228))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&cap;', NCHAR(0x2229))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&cup;', NCHAR(0x222a))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&int;', NCHAR(0x222b))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&there4;', NCHAR(0x2234))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sim;', NCHAR(0x223c))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&cong;', NCHAR(0x2245))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&asymp;', NCHAR(0x2248))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ne;', NCHAR(0x2260))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&equiv;', NCHAR(0x2261))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&le;', NCHAR(0x2264))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&ge;', NCHAR(0x2265))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sub;', NCHAR(0x2282))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sup;', NCHAR(0x2283))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&nsub;', NCHAR(0x2284))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sube;', NCHAR(0x2286))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&supe;', NCHAR(0x2287))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&oplus;', NCHAR(0x2295))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&otimes;', NCHAR(0x2297))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&perp;', NCHAR(0x22a5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&sdot;', NCHAR(0x22c5))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&lceil;', NCHAR(0x2308))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&rceil;', NCHAR(0x2309))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&lfloor;', NCHAR(0x230a))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&rfloor;', NCHAR(0x230b))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&lang;', NCHAR(0x2329))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&rang;', NCHAR(0x232a))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&loz;', NCHAR(0x25ca))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&spades;', NCHAR(0x2660))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&clubs;', NCHAR(0x2663))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&hearts;', NCHAR(0x2665))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&diams;', NCHAR(0x2666))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&gt;', NCHAR(0x003E))
	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '&lt;', NCHAR(0x003C))

	SELECT @vcResult = REPLACE(@vcResult COLLATE Latin1_General_CS_AS, '<p>', @vcCrLf)

	RETURN @vcResult
END
GO

UPDATE Project SET ProjectName = REPLACE(ProjectName, '&#39;', '''') WHERE ProjectName LIKE '%&%';
UPDATE Project SET ProjectName = dbo.HTMLDecode(ProjectName);
PRINT 'ProjectName updated'
GO
UPDATE Client SET ClientName = REPLACE(ClientName, '&#39;', '''') WHERE ClientName LIKE '%&%';
UPDATE Client SET ClientName = dbo.HTMLDecode(ClientName);
PRINT 'ClientName updated'
GO
UPDATE SalesOrganization SET SalesOrganizationName = REPLACE(SalesOrganizationName, '&#39;', '''') WHERE SalesOrganizationName LIKE '%&%';
UPDATE SalesOrganization SET SalesOrganizationName = dbo.HTMLDecode(SalesOrganizationName);
PRINT 'SalesOrgnaization Updated'
GO
UPDATE ProjectStatus SET Summary = REPLACE(Summary, '&#39;', '''') WHERE Summary LIKE '%&%';
GO
UPDATE ProjectStatus SET Summary = dbo.HTMLDecode(Summary) WHERE Summary LIKE '%&%';
PRINT 'ProjectStatus Summary Updated'
GO
UPDATE ProjectStatus SET ScheduleComments = REPLACE(ScheduleComments, '&#39;', '''') WHERE ScheduleComments LIKE '%&%';
GO
UPDATE ProjectStatus SET ScheduleComments = dbo.HTMLDecode(ScheduleComments) WHERE ScheduleComments LIKE '%&%';
PRINT 'ProjectStatus ScheduleComments Updated'
GO
UPDATE ProjectStatus SET CSATComments = REPLACE(CSATComments, '&#39;', '''') WHERE CSATComments LIKE '%&%';
GO
UPDATE ProjectStatus SET CSATComments = dbo.HTMLDecode(CSATComments) WHERE CSATComments LIKE '%&%';
PRINT 'ProjectStatus CSATComments Updated'
GO
UPDATE ProjectStatus SET ResourcesComments = REPLACE(ResourcesComments, '&#39;', '''') WHERE ResourcesComments LIKE '%&%';
GO
UPDATE ProjectStatus SET ResourcesComments = dbo.HTMLDecode(ResourcesComments) WHERE ResourcesComments LIKE '%&%';
PRINT 'ProjectStatus ResourcesComments Updated'
GO
UPDATE ProjectStatus SET ProjectRiskComments = REPLACE(ProjectRiskComments, '&#39;', '''') WHERE ProjectRiskComments LIKE '%&%';
GO
UPDATE ProjectStatus SET ProjectRiskComments = dbo.HTMLDecode(ProjectRiskComments) WHERE ProjectRiskComments LIKE '%&%';
PRINT 'ProjectStatus ProjectRiskComments Updated'
GO
UPDATE ProjectStatus SET BudgetComments = REPLACE(BudgetComments, '&#39;', '''') WHERE BudgetComments LIKE '%&%';
GO
UPDATE ProjectStatus SET BudgetComments = dbo.HTMLDecode(BudgetComments) WHERE BudgetComments LIKE '%&%';
PRINT 'ProjectStatus BudgetComments Updated'
GO
