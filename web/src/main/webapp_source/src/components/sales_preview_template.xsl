<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output omit-xml-declaration="yes" indent="yes"/>

	 <xsl:template match="itemOverview">
		 <table class="dashboard-table">
		   <tbody>
			   <xsl:apply-templates select="overview"/>
		   </tbody>
		 </table>
	 </xsl:template>
	 <xsl:template match="itemOverview/overview">
		 <tr>
			 <td><xsl:value-of select="dateStart" /></td>
			 <td><xsl:value-of select="item/name" /></td>
			 <td><xsl:value-of select="countSold" /><xsl:text> </xsl:text><xsl:value-of select="item/unit" /></td>
			 <td><small><xsl:value-of select="item/ean" /></small></td>
		 </tr>
	 </xsl:template>

	 <xsl:template match="categoryOverview">
		 <table class="dashboard-table">
		   <tbody>
			   <xsl:apply-templates select="overview"/>
		   </tbody>
		 </table>
	 </xsl:template>
	 <xsl:template match="categoryOverview/overview">
		 <tr>
			 <td><xsl:value-of select="dateStart" /></td>
			 <td><xsl:value-of select="countSold" /></td>
		 </tr>
	 </xsl:template>

</xsl:stylesheet>