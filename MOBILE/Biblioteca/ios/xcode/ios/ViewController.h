//
//  ViewController.h
//  ios
//
//  Created by Wallace Augusto on 23/08/17.
//  Copyright © 2017 stf. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
- (IBAction)sendToSegue:(UIButton *)sender;
@property (weak, nonatomic) IBOutlet UILabel *titulo;

@end
