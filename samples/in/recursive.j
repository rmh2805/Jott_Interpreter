Void foo( Integer x ){ if ( x > 0 ) {
                            foo( x - 1 );
                        }
                       print( x );}

foo( 10 );