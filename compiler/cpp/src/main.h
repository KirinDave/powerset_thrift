// Copyright (c) 2006- Facebook
// Distributed under the Thrift Software License
//
// See accompanying file LICENSE or visit the Thrift site at:
// http://developers.facebook.com/thrift/

#ifndef T_MAIN_H
#define T_MAIN_H

#include <string>
#include "parse/t_const.h"
#include "parse/t_field.h"

/**
 * Defined in the flex library
 */

int yylex(void);

int yyparse(void);

/**
 * Expected to be defined by Flex/Bison
 */
void yyerror(char* fmt, ...);

/**
 * Parse debugging output, used to print helpful info
 */
void pdebug(char* fmt, ...);

/**
 * Parser warning
 */
void pwarning(int level, char* fmt, ...);

/**
 * Failure!
 */
void failure(const char* fmt, ...);

/**
 * Check constant types
 */
void validate_const_type(t_const* c);

/**
 * Check constant types
 */
void validate_field_value(t_field* field, t_const_value* cv);

/**
 * Converts a string filename into a thrift program name
 */
std::string program_name(std::string filename);

/**
 * Gets the directory path of a filename
 */
std::string directory_name(std::string filename);

/**
 * Get the absolute path for an include file
 */
std::string include_file(std::string filename);

/**
 * Flex utilities
 */

extern int   yylineno;
extern char  yytext[];
extern FILE* yyin;

#endif
