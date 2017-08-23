//
//  ViewSegue2Controller.h
//  ios
//
//  Created by Wallace Augusto on 23/08/17.
//  Copyright © 2017 stf. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewSegue2Controller : UIViewController
- (IBAction)sendToSegue:(UIButton *)sender;
@property (weak, nonatomic) IBOutlet UILabel *titulo;

@end
